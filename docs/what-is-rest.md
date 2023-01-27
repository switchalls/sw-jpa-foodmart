# What is REST?

## Overview

from [what is REST?](https://restfulapi.net/)

> RE(presentational) S(tate) T(ransfer) ... architectural style for distributed hypermedia systems

Constraints for achieving a uniform REST interface:

| Constraints                                       | Description                                                                                         |
|---------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| Identification of resources                       | The interface must uniquely identify each resource                                                  |
| Manipulation of resources through representations | The resources should have uniform representations in the server response                            |
| Self-descriptive messages                         | Each resource representation should carry enough information to describe how to process the message |
| Hypermedia as the engine of application state     | The client should have only the initial URI of the application                                      |

### Further Reading

* [Roy Fielding dissertation](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm)

## Best Practise

> A typical design pattern with REST APIs is to build your endpoints based around resources. These are the “nouns” to HTTP method verbs. Your API design will be much easier to understand if these names are descriptive

from [stackoverflow.blog](https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/)

> We should enter a REST API with no prior knowledge beyond the initial URI (a bookmark) and a set of standardized media types appropriate for the intended audience
>
> From that point on, all application state transitions must be driven by the client selection of server-provided choices present in the received representations
>
> ... design REST APIs to be easy to understand for anyone consuming them, future-proof, and secure and fast ...
>
> * Accept and respond with JSON
> * Use nouns instead of verbs in endpoint paths
> * Name collections with plural nouns
> * Nesting resources for hierarchical objects
> * Handle errors gracefully and return standard error codes
> * Allow filtering, sorting, and pagination
> * Cache data to improve performance
> * Versioning our APIs

## HTTP Verb Usage

from [restapitutorial.com](https://www.restapitutorial.com/lessons/httpmethods.html#:~:text=The%20primary%20or%20most%2Dcommonly,but%20are%20utilized%20less%20frequently.)

| Verb   | CRUD           | Collection, eg. /customers                                                     | Item, eg. /customers/{id}                                 |
| ------ |----------------|--------------------------------------------------------------------------------|-----------------------------------------------------------|
| POST   | Create         | 201, 'Location' header with /customers/{id}                                    | 200 / 404 or 409 when resource exists                     |
| GET    | Read           | 200, list of customers                                                         | 200, single customer / 404 when {id} not found or invalid |
| PUT    | Update/Replace | 405, unless you want to update/replace every resource in the entire collection | 200 or 204 / 404 when {id} not found or invalid           |
| PATCH  | Update/Modify  | 405, unless you want to modify the collection itself                           | 200 or 204 / 404 when {id} not found or invalid           |
| DELETE | Delete         | 405, unless you want to delete the whole collection - not often desirable      | 200 / 404 when {id} not found or invalid                  |

But, from The [techtarget.com](https://www.techtarget.com/searchapparchitecture/tip/The-5-essential-HTTP-methods-in-RESTful-API-development)

```
Much like in PUT, it's poor practice to specifically apply PATCH methods to a whole resource collection - that is,
unless you truly intend to update every resource it contains.
```

Why? eg. race conditions where clients #1 and #2 constantly overwrite each others' lists

## Hypertext Application Language

from [weierophinney.github.io](https://weierophinney.github.io/hal/hal/)

> Hypertext Application Language (HAL) is a proposed IETF specification for representing API resources and their relations with hyperlinks

```
{
    "_links": {
        "self": { "href": "/api/books?page=7" },
        "first": { "href": "/api/books?page=1" },
        "prev": { "href": "/api/books?page=6" },
        "next": { "href": "/api/books?page=8" },
        "last": { "href": "/api/books?page=17" }
        "search": {
            "href": "/api/books?query={searchTerms}",
            "templated": true
        }
    },
    "_embedded": {
        "book": [
            {
                "_links": {
                    "self": { "href": "/api/books/1234" }
                }
                "id": 1234,
                "title": "Hitchhiker's Guide to the Galaxy",
                "author": "Adams, Douglas"
            },
            {
                "_links": {
                    "self": { "href": "/api/books/6789" }
                }
                "id": 6789,
                "title": "Ancillary Justice",
                "author": "Leckie, Ann"
            }
        ]
    },
    "_page": 7,
    "_per_page": 2,
    "_total": 33
}
```

### Further Reading

* [IETF specification](https://datatracker.ietf.org/doc/html/draft-kelly-json-hal)
* [REST APIs must be hypertext-driven](https://roy.gbiv.com/untangled/2008/rest-apis-must-be-hypertext-driven)
* [REST with Hypermedia - Hot Or Not?](https://reflectoring.io/rest-hypermedia/)

## Design Patterns

### For Things That Are Not Resources

from [Making API calls that don't involve resources](https://skryvets.com/blog/2020/07/06/rest-and-action-calls/)

* Convert verbs into nouns, eg. substitute `/pay` with `/payments`
* Determine the response of an action / name the action after what is created, eg. `POST /cipher`
* Refactor to utilize GET, eg. `GET /cipher/base64/HelloWorld`
* Use GET with request body

### Adding Long Running Jobs

from [tyk.io](https://tyk.io/blog/api-design-guidance-long-running-background-jobs/), eg.

```
> POST /accounts/bulk-import
HTTP/1.1 202 Accepted
Location: https://api.example.com/v1/import-jobs/7937

> GET /v1/import-jobs/7937
```