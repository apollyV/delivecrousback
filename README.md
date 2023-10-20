# Back de Delivecrous

### Membres du projet : 
- Constant Vennin
- Lucas Senez
- Thomas Nigault
- Maëlle Loyer

### Choix de l'architecture : 

L'architecture est la même que celle utilisée pour les tutoriels du cours.
Nous l'avons conservé pour notre back car Springboot est un framework facilitant énormément le développement d'API, auquel nous avons ajouté Lombok. Ces deux outils nous permettant d'écrire des annotations qui nous épargne de nombreuses lignes de code.

### Comment lancer le backend :

git clone https://github.com/apollyV/delivecrousback.git

ouvrez sur IntelliJ et lancez le projet en tant que projet maven. Faite un mvn clean install -DskipTests

OU

mvn clean install -DskipTests
mvn spring-boot:run

### Notes supplémentaires : 

Vous pouvez tester les requêtes sur Postman à l'aide du fichier tester requetes du back.postman_collection.json à importer dans Postman.

Effectuez les requêtes dans le bon ordre (sauf les deux finalize, il faut à nouveau ajouter un plat après chaque finalize) et n'oubliez pas d'utiliser le token renvoyé lors du login.