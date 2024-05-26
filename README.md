# Robot miner

## Developpement Environnement
- JavaFx
- Maven 3.9.1
- JDK 21
- Junit
### Description de l'application de jeu :


-Contrôles du jeu : Utilisez le clavier pour contrôler les robots :

q : Aller à gauche.

s : Aller en bas.

z : Aller en haut.

d : Aller à droite.

f : Déposer les ressources dans l'entrepôt.

r : Récolter des ressources dans la mine.



-Déplacement et actions :

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



