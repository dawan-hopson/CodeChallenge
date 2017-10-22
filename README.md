"# GitHub Api Endpoints"

This project has an API endpoint that accepts a GitHub ID and returns follower GitHub ID?s (up to 5 followers total)
associated with the passed in GitHub ID. It retrieves data up to 3 levels deep, repeating the process
of retrieving followers (up to 5 followers total) for each follower found.

There is also an API endpoint that will return a GitHub ID from passing a user login name.

The project has a swagger-ui page available at http://localhost:8080/swagger-ui.html for testing. After cloning run
the Application main class and visit the swagger url above to verify results of the two endpoints.