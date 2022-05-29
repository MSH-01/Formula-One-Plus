**Mobile Development 2021/22 Portfolio**
# API description

Your username: `C21011322`

Student ID: `21011322`

_For UI components, I stuck with material design because of the consistency in how widgets and elements looked, however I decided to use colours and fonts outside of the design guidelines, because they better fit my requirements and improved user experience. The initial wireframes were designed using the material components provided by Google and so I knew what elements to use when it came to development._

_I contemplated creating my own API for data scraping of various formula one websites, however found that the APIs available were suitable for my implmentation, except from some minor details. I found this was a good choice because the data response object was consistent and the services I developed to parse the data could be reused easily across different API sources._

_There was a need for me to persist some data in the app, where it would be wasteful to keep calling the API upon re-rendering the activity and so I decided to use sharedPreferences. This is because the data I was saving was generally small, pre-parsed snippets of data which could easily be set to a textView's content. There was no need for me to use a Room because the I maintain a consistent data set along the lifecycle of the app. The number of variables doesnt change so the use of a Room would've been an over-engineered solution._

_For API requests, I used Volley and Picasso because of how well they were able to handle requests, with different parameters and headers. Picasso was especially helpful with requesting image URIs and assigning the image to an ImageView.With Volley I could create an interface with a callback function so I could handle asynchronous API retrievals and manipulate the data._