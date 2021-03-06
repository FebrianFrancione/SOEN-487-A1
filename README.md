<h1>SOEN-487-A1</h1>

Created by: <i>Ivan Gerasymenko 27006284</i>, <i>Febrian Francione 40049253</i>, <i>Kiho Lee 40073402 (Team Leader)</i>

<h2>Album Requests:<h2>
  <p>Port running on 8080 - separated form the servlet's 8980</p>
<ul>
  <li>GET: http://localhost:8080/core/album/{ISRC}/{title}</li>
  <li>GET ALL ALBUMS by ISRC and Title: http://localhost:8080/core/album/list</li>
  <li>POST: http://localhost:8080/core/album/create/{ISRC}/{title}/{description}/{year}/{artist}</li>
  <li>DELETE: http://localhost:8080/core/album/{ISRC}</li>
  <li>PUT: http://localhost:8080/core/album/{ISRC}/{title}/{description}/{year}/{artist}</li>
</ul>
  
<h4> cURL Requests from Album Service </h4>
<p>Servlet integration using Tomcat</p>

<h5> When using cURL with Albums, if no description is provided, a null must be set</h5>
<ul>
  <li>GET: curl -v http://localhost:8080/core/album/{ISRC}/{title}</li>
  <li>GET ALL ALBUMS by ISRC and Title: curl -v http://localhost:8080/core/album/list</li>
  <li>POST: curl -v -d POST http://localhost:8080/core/album/create/{ISRC}/{title}/{description}/{year}/{artist}
  <li>DELETE: curl -v -X DELETE http://localhost:8080/core/album/{ISRC}</li>
  <li>PUT: curl -v -X PUT http://localhost:8080/core/album/{ISRC}/{title}/{Descritpion}/{year}/{artist} - where ISRC is the required ID needed to find and modify the album</li>
</ul>

<h2>Artist Requests:<h2>
 <p>Please run the Tomcat servlet with a different port than you use the Jersey services.</p>
 <br>
 <p>I chose port 8980, you can choose any other port you wish.</p>
  <h5>if no biography is provided, an empty space can be used.</h5>
 
<ul>
  <li>GET by nickname: curl -v -G http://localhost:8980/demo_war/artists --data-urlencode "nickname={nickname}"</li>
  <li>GET ALL ARTISTS: curl -v http://localhost:8980/demo_war/artists</li>
  <li>POST: curl -v -d "nickname={nickname}&first_name={first_name}&last_name={last_name}&biography={biography}" http://localhost:8980/demo_war/artists</li>
  <li>DELETE: curl -v -X DELETE http://localhost:8980/demo_war/artists?nickname={nickname}</li>
  <li>PUT: curl -v -X PUT -d "nickname={nickname}#first_name={first_name}#last_name={last_name}#biography={biography}" http://localhost:8980/demo_war/artists</li>
</ul>
