# Sarafan

------------------

![](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java)
![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate)
![](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white)
![](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle)
![](https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=Vue.js&logoColor=white)
![](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=Vuetify)
![](https://img.shields.io/badge/Webpack-F16822?style=for-the-badge&logo=Webpack&logoColor=white)
![](https://img.shields.io/badge/Yarn-2C8EBB?style=for-the-badge&logo=Yarn&logoColor=white)
![](https://img.shields.io/badge/Sentry-362D59?style=for-the-badge&logo=Sentry)

-----------

It`s simple REST App. This app use 4 main methods (GET, POST, PUT, DELETE) <br/>
and use a websocket to notify other users about changes.

In app your can create posts, like in Facebook, and also comment them.

To see posts other users, your must subscribe on them, and, 
if they(users) want to show their posts, 
they must confirm your request

--------

### To see the project, click [HERE](https://sarafan-houston.herokuapp.com) 
(Free server - of course slowly ) <br/> <br/>

##### To find my profile, click [here](https://sarafan-houston.herokuapp.com/user/103707175637626812043)

##### [Here](https://sarafan-houston.herokuapp.com/user/108210505017070379863) my second account

----------

If you have some problems - don`t write to me

### Change log
* v0.2 Alpha
  - Some changes in UI
  - Add all users page
  - Fixed bugs with changes without access
  - Preparing for chats
* v0.1 Alpha
  - Deploy to Heroku
* v0.07
  - Add Sentry logging
  - Small bug fix
* v0.06
  - Activate or Dismiss subscriptions
* v0.05
  - fixed bugs with comments
  - Add subscription
  - U can see only your and your subscribe message
* v0.03
  - Add comments (have some bugs)
* v0.02
  - Add Profile page
  - fixed some bugs
  - some changes for stable working
* v0.01
  - Make first Styling with Vuetify
  - oAuth2 authorization (Google)
  - WebSocket (SockJS + Stomp)
  - Makes webpack with yarn


### If you want to lunch this project follow this instruction
 Need: Node.js, Yarn, Java, Postgres

1. Environment variables ```spring.profiles.active=dev;clientSecret=DBi_DsO80FF_mG4xmU26uawy``` 
2. Create DB "sarafan" & Change or set this Login & Password to DB 
```
   spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost/sarafan}
   spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
   spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:123} 
   ```
3. Lunch Project and webpack ```yarn start```
 
