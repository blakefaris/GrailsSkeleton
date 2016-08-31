# Basic Grails Web Application
Lightweight web application that provides simple starter endpoints to handle various content types.  
Never expect client to build their own URLs to hit the endpoints.
HATEOAS, always return appropriate endoints.
Build the API first, do not build it with a UI.

* Example of known home URL: https://www.example.com
* Example of what will **NOT** be generated: https://www.example.com/register
* Instead the responses from the API will provide available URLs
```json
{
  "links": {
    "auth": {
      "href": "https://www.example.com/auth"
    },
    "register": {
      "href": "https://www.example.com/register"
    },
    "forgotPassword": {
      "href": "https://www.example.com/forgot"
    }
  }
}
```

## Services
* Vanity URLs

## Planned Services
* Managed/Target Content
* Promo 
* Meta Tabs
* Do Not Include

## Setup

### Create Project
```sh
$ grails create-app basic-grails-app
```

### Import into IntelliJ
```
New > Project from Existing Source
```
Set Grails SDK Home
```
Preferences > Languages & Frameworks > Grails
Grails SDK Home: /Users/example/.gvm/candidates/grails/current
```



