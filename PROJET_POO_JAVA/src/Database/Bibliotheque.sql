-- 1. Création et utilisation de la base
CREATE SCHEMA IF NOT EXISTS `bibliotheque` DEFAULT CHARACTER SET utf8mb4 ;
USE `bibliotheque` ;
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id_utilisateur` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL,
  `prenom` VARCHAR(100) NOT NULL,
  `matricule` VARCHAR(45) NOT NULL,
  `type_utilisateur` ENUM('ETUDIANT', 'ENSEIGNANT') NOT NULL,
  PRIMARY KEY (`id_utilisateur`),
  UNIQUE INDEX `matricule_UNIQUE` (`matricule` ASC))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `livres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `livres` (
  `id_livre` INT NOT NULL AUTO_INCREMENT,
  `titre` VARCHAR(255) NOT NULL,
  `auteur` VARCHAR(150) NOT NULL,
  `isbn` VARCHAR(20) NOT NULL,
  `quantite_totale` INT NOT NULL DEFAULT 1,
  `quantite_disponible` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_livre`),
  UNIQUE INDEX `isbn_UNIQUE` (`isbn` ASC),
  CONSTRAINT `chk_quantite` CHECK (quantite_disponible <= quantite_totale AND quantite_disponible >= 0))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `emprunts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `emprunts` (
  `id_emprunt` INT NOT NULL AUTO_INCREMENT,
  `id_utilisateur` INT NOT NULL,
  `id_livre` INT NOT NULL,
  `date_emprunt` DATE NOT NULL,
  `date_retour_prevue` DATE NOT NULL,
  `date_retour_effective` DATE NULL,
  `penalite` DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (`id_emprunt`),
  INDEX `fk_emprunt_user_idx` (`id_utilisateur` ASC),
  INDEX `fk_emprunt_livre_idx` (`id_livre` ASC),
  CONSTRAINT `fk_utilisateur`
    FOREIGN KEY (`id_utilisateur`)
    REFERENCES `utilisateurs` (`id_utilisateur`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_livre`
    FOREIGN KEY (`id_livre`)
    REFERENCES `livres` (`id_livre`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
DELIMITER //

CREATE TRIGGER before_emprunt_insert
BEFORE INSERT ON emprunts
FOR EACH ROW
BEGIN
    DECLARE v_type VARCHAR(20);
    DECLARE v_count INT;
    DECLARE v_stock INT;

    -- 1. Récupérer le type de l'utilisateur et son nombre d'emprunts en cours
    SELECT type_utilisateur INTO v_type FROM utilisateurs WHERE id_utilisateur = NEW.id_utilisateur;
    SELECT COUNT(*) INTO v_count FROM emprunts WHERE id_utilisateur = NEW.id_utilisateur AND date_retour_effective IS NULL;

    -- 2. Récupérer le stock disponible du livre
    SELECT quantite_disponible INTO v_stock FROM livres WHERE id_livre = NEW.id_livre;

    -- Règle 5 : Vérifier si le livre est disponible
    IF v_stock <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erreur : Ce livre n est plus disponible.';
    END IF;

    -- Règle 1 & 2 : Vérifier les quotas
    IF v_type = 'ETUDIANT' AND v_count >= 3 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erreur : Un étudiant ne peut pas avoir plus de 3 emprunts.';
    ELSEIF v_type = 'ENSEIGNANT' AND v_count >= 5 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erreur : Un enseignant ne peut pas avoir plus de 5 emprunts.';
    END IF;

    -- Règle 3 : Calcul automatique de la date de retour prévue (Date Emprunt + 14 jours)
    -- Si la date n'est pas fournie par Java, on la met par défaut
    IF NEW.date_retour_prevue IS NULL THEN
        SET NEW.date_retour_prevue = DATE_ADD(NEW.date_emprunt, INTERVAL 14 DAY);
    END IF;
END //

DELIMITER ;
DELIMITER //

CREATE TRIGGER after_emprunt_insert
AFTER INSERT ON emprunts
FOR EACH ROW
BEGIN
    UPDATE livres 
    SET quantite_disponible = quantite_disponible - 1 
    WHERE id_livre = NEW.id_livre;
END //

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_emprunt_update_return
BEFORE UPDATE ON emprunts
FOR EACH ROW
BEGIN
    DECLARE v_jours_retard INT;

    -- On vérifie si on est en train d'enregistrer un retour (date_retour_effective passe de NULL à une valeur)
    IF NEW.date_retour_effective IS NOT NULL AND OLD.date_retour_effective IS NULL THEN
        
        -- Calcul de la différence de jours entre le retour réel et le retour prévu
        SET v_jours_retard = DATEDIFF(NEW.date_retour_effective, OLD.date_retour_prevue);

        -- Règle 4 : Si retard, pénalité = 1 * nombre de jours
        IF v_jours_retard > 0 THEN
            SET NEW.penalite = v_jours_retard * 1; -- Change "1" par la valeur XX de ton choix
        ELSE
            SET NEW.penalite = 0;
        END IF;
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_emprunt_update_return
AFTER UPDATE ON emprunts
FOR EACH ROW
BEGIN
    -- Si le livre est rendu, on remet +1 dans le stock
    IF NEW.date_retour_effective IS NOT NULL AND OLD.date_retour_effective IS NULL THEN
        UPDATE livres 
        SET quantite_disponible = quantite_disponible + 1 
        WHERE id_livre = NEW.id_livre;
    END IF;
END //

DELIMITER ;