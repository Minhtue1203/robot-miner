# Robot miner :robot:

- SAE 2.01 - [feat/sae-201](https://github.com/Minhtue1203/robot-miner/tree/feat/sae-201)
- SAE 2.02 - [master](https://github.com/Minhtue1203/robot-miner/tree/master)

## Developpement Environnement :gear:
- JavaFx
- Maven 3.9.1
- JDK 21
- Junit

## Lancer l'application :rocket:

- Lancer manuellement via Intellij
- /out/artifacts/robot_miner_jar/robo_miner.jar ne fonctionne pas en raison suivant:
  Error: JavaFX runtime components are missing, and are required to run this application (en cours d'analyse)

## Description de l'application de jeu :book:

### Objets et obstacles sur la carte :world_map:
- Eau : Représentée par un carré bleu
- Mine de nickel : Représentée par un carré gris
- Mine d'or : Représentée par un carré jaune
- Entrepôt d'or : Représenté par un carré orange
- Entrepôt de nickel : Représenté par un carré noir
- Robot mineur de nickel/d'or: Sa couleur sera générée de manière aléatoire et unique.

La position de chaque objet (eau, mines, entrepôts, robots) est gérée de manière aléatoire et change à chaque fois que le jeu est relancé.

### Restrictions de déplacement :stop_sign:
Les robots ne peuvent pas se déplacer sur des cases d'eau.

Chaque robot ne peut effectuer qu'une seule action par tour.
- Déplacement
- Récolte
- Déposer

### Contrôles du jeu :runner:

- Utilisez le clavier pour déplacer le robot

<kbd><br>Z<br></kbd> : Aller en haut :arrow_up:

<kbd><br>S<br></kbd> : Aller en bas :arrow_down:

<kbd><br>Q<br></kbd> : Aller à droite :arrow_right:

<kbd><br>D<br></kbd> : Aller à gauche :arrow_left:

<kbd><br>F<br></kbd> : Déposer les ressources dans l'entrepôt :house:

<kbd><br>R<br></kbd> : Récolter des ressources dans la mine :pick:
