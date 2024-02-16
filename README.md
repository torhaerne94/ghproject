## ghproject
Simple application used to get data about user's 
repositories and their branches.


## technologies
* spring-boot-starter v 3.2.2
* java openjdk-21

## external api
api provided by github:
https://developer.github.com/v3
was used to get data.

## usage examples
example requests for postman to use api:
* http://localhost:8080/app/api/user/wewqoekwekwoekwekwoe/repositories - not existing user
* http://localhost:8080/app/api/user/torhaerne94/repositories - existing user

in case of not fining user api should return Http status 404 with response:

{
    "responseCode": "NOT_FOUND",
    "message": "user not exists"
}

in case of fining user api should return Http status 200 with response:
[
    {
    "repositoryName": "",
    "ownerLogin": "",
    "branchesCommit": [
        {
            "name": "",
            "lastCommitSha": ""
        }, ...
    ]
    } ...
]

## project status
for now api handles just 404_NOT_FOUND errors (rest will be developed in future)