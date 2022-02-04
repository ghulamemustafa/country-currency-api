# Country & Currency Info APP with Spring Boot’s inbuilt OAuth2 Resource Server (JWT)


## Solution Overview


## Getting Started

- Use `mvn clean install` in the project root directory to build the project. 
- Run the main class, `com.gm.kitalulus.CountryAndCurrencyInfoApplication` to start the application.

## Endpoints

- `/login` -> Public end-point which returns a signed JWT(OAuth2) for valid user credentials 

```
curl --location --request POST 'http://localhost:8080/login' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=user123' \
--data-urlencode 'password=1234' 
```


- `/country/{countryName}` -> A protected end-point which returns the country & currency info by name of the country `countryName` length should be between `3-100` to avoid too-many conversion calls as the free quota is limited

```
curl --location --request GET 'http://localhost:8080/country/pak' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibmJmIjoxNjQzODczMzQ2LCJleHAiOjE2NDM5NTk3NDYsInVzZXJJZCI6IjEiLCJhdXRob3JpdGllcyI6IlVTRVIiLCJ1c2VybmFtZSI6InVzZXIifQ.SJU32w1GEPNN1TzQlQYzoIZiIbMyPXLEo_w-IEXjSra3LmuazLNrvAbpBU62Gzx3-m3nrDMJvCjsQPT6og_v7Uthv-hEig12Pc8cusqE8mbyOVvitml4QO_MnjU0A-aa6J5bGXPIGkwqCX8HvHss4jQacsZwgsQJPidEP2o3NatmemI5_E67Bc6w3biF2iSYcZ3Lvs1tT38PwS5_XI90-YReWL_S5tuVXXRgnWHqEwxs31n88Jg0noODUQBpmd1wPoGJ3b8iQafjWhXyVvBZk8feNtadNu8aB8-hi8oyRjjqyoEE1y75AHsZ4m44MgFCXnu1gUITKEmQs9a3oF4Wjw'
```

# application.properties

Most of the properties are self explaining but I'd like to discuss following:

- `app.integration.countryinfo.api.fullname.field.path` can be changed to any desired field in country info api response eg. `/name/common` maps the `name.common` field as `fullName`
- `app.base.currency` can be used to change the base currency to the desired currency code (refer to currency API document for supported currency codes)

# Rate-Limiting

I assumed the incoming calls needed to be rate-limited. Hence I've used `Bucket4j`for said purpose. For the currency API rate limiting, caching is used.

- `app.countryinfo.api.ratelimit.perminute` can be used to change the countryAPI rate limit per-minute

**Rate-limiting can be tested by following script :**

you may update number of requests and JWT accordingly !

```
for i in `seq 1 40`; do curl -i --location --request GET 'http://localhost:8080/country/PAK' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzIiwibmJmIjoxNjQzOTA2MjQ2LCJleHAiOjE2NDM5OTI2NDYsInVzZXJJZCI6IjEiLCJhdXRob3JpdGllcyI6IlVTRVIiLCJ1c2VybmFtZSI6InVzZXIxMjMifQ.HbgqL29sIAcz5NTRuG6ELQfNT9kVwtHHOtJ7xCZErxZQTPCL9X-q1jEXkI88rUAzxzAJEMDfc_FcwA8Bb9WuBkDqO6dhaYViGrivJvWlLfJ9R09smOfNb5wFEE5RhIWAdU5RngUAEoY215rSohfWQHCW_myCuPz7ChRpO32EFHXnf6656RYD7YKX4W10FCxar9cvZwEQ6xtSFNYvbbEjBkoAIrPaNKjq-SA4SCFsY8aQJ6z_GnkLAUGK9bOtKz3lFHABP7dHnpdbFJzjR0od5Y86mQPy29J6OEjHWtsBnTSXyzrmeJXmYSctEgCDaNHi7xabd2LU1JnmuGbO7wT0sQ'; done

```


 
# Caching

Since the currency conversion API has limited quota for free user and the conversion rate don't change that often, I have used a caching layer to reduce number of calls to the said API. The current cache ttl is 30 min i.e cache expires every 30 min and new data is fetched afterwards. This cache ttl can be controlled via `ehcache.xml` file.

Moreover the cache events can be seen in the logs for better understanding.

# Auditing

For the sake of auditing I've used logging (./log/spring.log) to record every outgoing API request along with the user details. This can be quite useful to trace user activity for the said APIs.

- `app.integration.countryinfo.api.logging` can be used to enable/disable country info requests/response logging
- `app.integration.currencyinfo.api.logging` can be used to enable/disable currency info requests/response logging

