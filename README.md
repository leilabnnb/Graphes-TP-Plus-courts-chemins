# TP3 - Plus courts chemins

*Fait par : Leila Bennabi*


Le problème du plus court chemin est un problème algorithmique qui 
consiste à trouver un chemin d’un sommet à un autre de façon que la
somme des poids des arêtes de ce chemin soit minimale.
Il existe de nombreuses variantes de ce problème suivant que le 
graphe est orienté ou non et suivant la nature des poids des arêtes.

L'algorithme qui nous concerne sera celui de Dijkstra, applicable sur
des graphes orienté ou non, mais avec une condition dur les poids des
arêtes (et des sommets éventuellement dans certaines variantes) qui
doivent être positifs.

L'objectif de ce TP est d'arriver à faire une comparaison (et en tirer des conclusions) de deux versions
de cet algorithme, la première a été implanté par moi-même pour les besoins du TP
et la seconde est celle de la bibliothèque de GraphStream.

Pour appliquer ces algorithmes nous auront besoin de générer des graphes
pondérés facilement et dont on peut changer les caractéristiques 
facilement.

## Générateur
Cela sera possible grâce à l'utilisation d'un générateur
de graphes. J'ai donc créé une classe `GraphGenerator` composée 
trois méthodes statiques.

La plus importante est `GraphGenerator.generate(order, avgDegree, maxWeight, oriented)`,
elle prend en paramètre l'ordre du graphe souhaité, son degré moyen,
la borne maximale des poids de ses arêtes et un booléen spécifiant si
le graphe doit être orienté ou non. Avoir tous ces paramètres nous permet 
d'avoir plus de flexibilité dans la création des graphes souhaités. 
J'ai utilisé le `RandomGenerator` de GraphStream comme demandé en suivant 
les instructions et les exemples de code sur le site.
Ma classe de générateur contient également une méthode `GraphGenerator.styleAndDisplay(g, NodesColor)` 
permettant d'afficher un graphe passé en paramètre.

## Algorithme de Dijkstra version naïve
Pour ma version de l'algorithme, j'ai suivi le cours et la [page Wikipédia
de l'algorithme de Dijkstra](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) j'ai créé une classe `MyDijkstra` avec quatres 
attributs de classe : le graphe sur lequel l'algorithme est appliqué, le nœud source
par lequel commence la recherche, une file de priorité pour ça j'ai utilisé un objet de type
`PriorityQueue` de la bibliothèque Java pour simplifier le code grâce aux méthodes
que la classe permet d'utiliser et aussi parce que c'est plus efficace car le noeud
avec la plus petite distance est toujours en premier, et un `Set` également 
pour stocker les nœuds visités. 
En plus d'instancier chaque attribut, le constructeur ajoute des attributs à chaque 
nœud du graphe.

La méthode `dijkstra()` parcours les noeuds non visités tant que la file de priorité 
n'est pas vide afin de calculer les chemins allant vers les voisins de ces noeuds 
depuis la source et permettre le traitement de tous les nœuds.


## Algorithme de Dijkstra version de GraphStream
Cette version utilise un tas de Fibonacci pour le stockage des données, le problème 
est résolu avec une complexité de l'ordre de O(n log(n) + m), avec n la taille du graphe
et m le nombre d'arêtes.
On l'utilise simplement de cette manière : 
```java
                Dijkstra theDijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "length");
                theDijkstra.init(g1);
                theDijkstra.setSource(s);
                start = System.nanoTime();
                theDijkstra.compute();
                end = System.nanoTime();
```

## Tests effectués

J'ai testé les deux algorithmes sur des types de graphes 
différents en faisant varier les tailles, les degrés moyens
et le fait que ce soit orienté ou non. 
En observant les résultats obtenus (que j'exposerai plus bas dans le document) je me suis aperçue
qu'il y avait une caractéristique sur laquelle je n'avais pas 
vraiment de contrôle avec mon code de départ (ou alors difficilement), la densité du graphe.
En effet, je pouvais à peu près contrôler la densité quand je générais les graphes individuellement,
mais ce n'était pas possible de le faire quand je testais en série des graphes de différentes tailles.
La valeur unique que je fixais pour le degré moyen me faisait obtenir des graphes d'ordres différents
de faible densité et d'autres trés denses, que je testais tous ensemble, j'avais donc trop de 
variables et je ne pouvais pas tirer de conclusions sur le lien entre la taille du graphe et le temps d'exécution.
Pour y remédier, j'ai apporté quelques modifications à la méthode me permettant de générer les graphes
pour choisir non pas le degré moyen, mais la densité du graphe.
Néanmoins, j'ai gardé la première également, car la mesure du temps d'exécution en fonction des variations de densité me semble intéressant aussi.

*Remarque* Je n'ai pas pû faire de tests impliquant la méthode fixant la densité sur de grands graphes, car j'avais une erreur `OutOfMemoryError` que je n'ai pas sû corriger.


## Résultats

### Graphes de petite taille

![](tp3-plus-courts-chemins/dataFiles/petits/avg_S_density0.1.png)
![](tp3-plus-courts-chemins/dataFiles/petits/avg_S_density0.5.png)
![](tp3-plus-courts-chemins/dataFiles/petits/avg_S_density0.9.png)


### Graphes de taille moyenne
![](tp3-plus-courts-chemins/dataFiles/moyens/avg_M_density0.1.png)
![](tp3-plus-courts-chemins/dataFiles/moyens/avg_M_density0.5.png)
![](tp3-plus-courts-chemins/dataFiles/moyens/avg_M_density0.9.png)

### Graphes de taille relativement grande
Je n'ai pas pû générer des données intéressantes pour des graphes de grandes tailles.


## Observations
On peut remarquer que : 
- Hormis quelques exceptions sur les graphes de petites 
tailles, l'algorithme de GraphStream est toujours plus efficace que la version naive.

- Les moyennes des temps d'exécution évoluent de la même
façon en dépit des différences de densités. (Ce n'est pas vraiment le sujet du TP,
mais Monsieur Sanlaville avait évoqué le lien entre la densité des graphes et l'exécution de Dijkstra, j'ai alors souhaité mettre cela en évidence)

  
## Explications

- L'utilisation du tas de Fibonacci rend l'algorithme de GraphStream plus efficace sur les grands graphes, ar le tas de Fibonacci a un temps d’exécution amorti constant, c’est-à-dire O(1), pour les opérations insert, find-minimum et decrease-key.





Ce TP est une façon de voir au-delà de la théorie abordée en cours, cela m'a permis de mieux comprendre et visualiser
certains résultats, notamment la quasi-indépendance des performances de l'algorithme de Dijkstra et les densités des graphs parcourus.














