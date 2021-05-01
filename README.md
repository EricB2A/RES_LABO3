# RES_LABO3

## DESCRIPTION :mailbox_with_mail: 
Dans le cadre de notre cours RES, nous avons dû réaliser un programme qui permet de réaliser une campagne de prank par e-mail,
c'est-à-dire, envoyer une série des par courrier électronique à plusieurs groupes de personnes en usurpant à chaque fois
l'identité d'un des membres de ces groupe pour envoyer l'e-mail le faisant ainsi passé pour l'expéditeur.


## DOCKER :whale2:
Pour mocker notre serveur SMTP, nous avons utilisé [MockMock](https://github.com/tweakers/MockMock).
Ce serveur est serveur se lance via un `jar` et, nous avons créé une image docker automatisant tout ça.

Configuration par défaut du serveur MockMock : 
| PROTOCOLE | PORT |
|-----------|------|
| SMTP      | 2500 |
| HTTP      | 8080 |

Et bien évidemment, vous pouvez changer ces ports à votre guise. Pour ce faire, référez vous aux fichiers [docker-compose.yml](MockMockDocker/docker-compose.yml) et [mock_server.Dockerfile](MockMockDocker/mock_server.Dockerfile).    
*Dans la balise port du docker-compose.yml vous trouverez la ligne "port1:port2". port1 correspond au port ouvert chez l'hôte et le port:2 au port ouvert dans le container*.

Ainsi, pour lancer le docker, il vous suffit de vous déplacer dans le dosser `MockMockDocker`, puis de lancer la commande suivante :   
```
docker compose up
```   
et vous avez un serveur SMTP qui tourne en local ! :sunglasses:

## COMPAGNE DE PRANK :fire:
Voici les étapes pour lancer une campagne de prank : 
1. Cloner ce repository.
2. Avoir accès à un serveur SMTP. Par exemple MockMock.
3. 

*TODO expliquer fichiers de configuration.*

```
mvn clean install
mvn exec:java
```



## IMPLÉMENTATION :mag:
*TODO*
NOTE: Faudrait ajouter l'UML ici ! 