# Robot miner :robot:

## Developpement Environnement 
- JavaFx
- Maven 3.9.1
- JDK 21
- Junit

## Description de l'application de jeu

### Contrôles du jeu

Utilisez le clavier pour contrôler les robots :

[<kbd><br>Z<br></kbd>][Link] : Aller en haut :arrow_up:

[<kbd><br>S<br></kbd>][Link] : Aller en bas :arrow_down:

[<kbd><br>Q<br></kbd>][Link] : Aller à droite :arrow_right:

[<kbd><br>D<br></kbd>][Link] : Aller à gauche :arrow_left:

[<kbd><br>F<br></kbd>][Link] : Déposer les ressources dans l'entrepôt :house:

[<kbd><br>R<br></kbd>][Link] : Récolter des ressources dans la mine :pick:


Déplacement et actions :

Lorsque le robot entre dans une mine, il "disparaît" visuellement de la grille dans le mode graphique, mais reste visible dans le mode console.
Le tour de chaque robot est affiché en haut de l'application.
Toutes les informations relatives à l'entrepôt, aux mines et aux robots sont affichées dans la console.
Objets et couleurs :

Eau : Représentée par un carré bleu.
Mine de nickel : Représentée par un carré gris.
Mine d'or : Représentée par un carré jaune.
Entrepôt d'or : Représenté par un carré orange.
Entrepôt de nickel : Représenté par un carré noir.
Robot mineur de nickel : Représenté par un carré rose.
Robot mineur d'or : Représenté par un carré vert.

-Positionnement des objets :

La position de chaque objet (eau, mines, entrepôts, robots) est gérée de manière aléatoire et change à chaque fois que le jeu est relancé.
Restrictions de déplacement :

Les robots ne peuvent pas se déplacer sur des cases d'eau.
Chaque robot ne peut effectuer qu'une seule action par tour.



