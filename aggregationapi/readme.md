# the aggreagtion api
This api is responsible for aggregating the pricing, tracking and shipping data.

# starting it
Run the docker-compose up -d command to spin up the postgress database and the rabbitmq.
Then run the application just use mvn clean package in the root this project and in the target folder java -jar <jar name>.

# Accessing the api
To access the data run the following command in a browser, postman or equivalent:
http://localhost:2013//pricing?q=NL,CN.
