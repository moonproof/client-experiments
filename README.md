<h3>Необходимое окружение</h3>

1. jdk-11
2. docker

<h3>Как запустить проект</h2>

1. Готовим артефакт в директория docker: `./gradlew bootJar`
2. И выполняем следующую команду для запучка: `docker build --tag=client-experiment . && docker run client-experiment`
   Проект поднят, состоит из spring-boot-application и postgres

Проект выполнен по задаче https://github.com/appbooster/test-assignments/blob/master/tasks/backend.md

<h4>Эндпоинты:</h4>
1. /api/experiment GET - для получения данных об эксперементах для девайса
2. /api/experiment/statistic GET - для получения статических данных об эксперементах