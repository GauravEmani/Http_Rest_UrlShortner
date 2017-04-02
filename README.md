# Http_Rest_UrlShortner
Url shortner with authentication

Download the application:

Run the following command in the project folder
mvn spring-boot: run

Steps:

1. Create an account
2. Register a url with redirectType
3. Check the URL statistics


To create an account run the following CURL command
-> curl -X POST -H "Content-Type:application/json" -d "{\"id\":\"123456\"}" http://localhost:8080/accounts

Expected Response : 
Authorization token in the response header tag.
{success: 'true', description: 'Your account is opened', password: 'xC345Fc0'}


Send a long url address, the response will be a shortened URL
Register a url:
-> curl -X POST -H "Authorization: Basic dGVzdDpwYXNzd29yZA==" -d "{\"url\":\"http://stackoverflow.com/questions/1567929/website-safe-data-access-architecture-question?rq=1",
\"redirectType\":\"301\"}" http://localhost:8080/register

Fetch the statistics of the based on accountID:
curl -X GET -H "Authorization: Basic dGVzdDpwYXNzd29yZA==" http://localhost:8080/statistic/123

For demo purpose this project does not make use of spring-security authentication, also the the generated token is based on 
user: test
password: password
hardcoded in the authentication filter
