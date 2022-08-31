# Formula One +

## Overview

_This app was designed to give formula one fans a better experience than provided by the official app. The app will allow users to view upcoming races, see latest formula one news, view driver/constructor standings and view the current season's formula one tracks._

_The official formula one app allows you to view current standings and latest news but doesn't give users a good way to interact with races / other general information. Formula one enthusiasts want to be reminded of when F1 events start, something the current app doesn't do._

_I decided to make this app because I felt like the official app didnt meet my own requirements as an F1 enthusiast. I want to include features in my app which I wish were part of the official app. I think this app has the potential to be a better option than the official app due to the extra features and superior UI design._

_Usability and design is a big focus for me, I want to make sure the app is easily accessible, not overly complex and not bloated with useless information._

## Requirements

#### Functional Requirements.
- The app must display latest news from formula1.com so users can read the articles.
- The app must show a countdown to the next race on the home page so the user can immediately see when the next race is.
- Both driver and constructor standings should be displayed and up to date.
- There should be a map view with the current season's formula one tracks marked.
  
#### Non-Functional Requirements.
- APIs should only be called again after race day to improve performance of the app.
- The app should be able to function with no internet connection (after first use).
- Any images must be async fetched as to not compromise usability.
- UI design should be clear and should match formula one design guidelines to better user experience.
- Widgets and UI elements should behave the same with different data.

## API description

_For UI components, I stuck with material design because of the consistency in how widgets and elements looked, however I decided to use colours and fonts outside of the design guidelines, because they better fit my requirements and improved user experience. The initial wireframes were designed using the material components provided by Google and so I knew what elements to use when it came to development._

_I contemplated creating my own API for data scraping of various formula one websites, however found that the APIs available were suitable for my implmentation, except from some minor details. I found this was a good choice because the data response object was consistent and the services I developed to parse the data could be reused easily across different API sources._

_There was a need for me to persist some data in the app, where it would be wasteful to keep calling the API upon re-rendering the activity and so I decided to use sharedPreferences. This is because the data I was saving was generally small, pre-parsed snippets of data which could easily be set to a textView's content. There was no need for me to use a Room because the I maintain a consistent data set along the lifecycle of the app. The number of variables doesnt change so the use of a Room would've been an over-engineered solution._

_For API requests, I used Volley and Picasso because of how well they were able to handle requests, with different parameters and headers. Picasso was especially helpful with requesting image URIs and assigning the image to an ImageView.With Volley I could create an interface with a callback function so I could handle asynchronous API retrievals and manipulate the data._

## Retrospective

_My application development was generally successful. I'm very happy with the UI and how the user interface is consistent across the application._

_The main features from the requirements are there for the most part, they were hindered by some difficulty using the Google Maps API alongside the Formula One API. This meant I had to change my approach to a more rigid, less elegant solution where I hard coded the track location details._

_If I had more development time, I would change the main home page to open a full screen dialog with further information form the main article, this didn't happen in the development timeframe because the API didn't include the article content - I had planned to create my own web scraper but ran out of time._

_I'm also happy with how the application compares the current time to the Date object of race day, each week, and re-requests the API responses after race day, updating the content throughout the app. With more time I could make this live update, I would need to make use of a python module and host a server._

