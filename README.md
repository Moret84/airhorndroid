#Airhorndroid

##Informations and usage

Airhorndroid is a simple android app to triggger airhorn calls on the awesome [hammerandchisel's airhorn discord bot](https://github.com/hammerandchisel/airhornbot) without have to type any text.  
It is very easy to use. First, open the Settings menu and enter your credentials. You will also need to provide the API key of the channel on which you want to !airhorn. This key can be fetched by the server admin.
Simply add your channel using the button and you will find it in the drop down list.  
Once you're done, just press Save and you won't have to go back to settings again since they will remain in your phone memory ;). 
The main screen holds 5 buttons : Login, 1, 2, 3, 4. Login allows you to automagically retrieve the authentication token using your credentials.  
A Toast will appear with your token inside (useless I agree). Then you can press 1, 2, 3, 4 buttons to make !airhorn calls. 
Currently mapping is as follow:
- 1 button -> !airhorn default
- 2 button -> !airhorn reverb
- 3 button -> !airhorn tripletap
- 4 button -> !airhorn fourtap

##Roadmap

- Make a kind of autologin and caching the token. As a result, the login button won't be needed anymore.
- Improve app design. Wear it with the airhorn bot colors ;)

##Dependencies

The app uses the mighty [android-async-http library](https://github.com/loopj/android-async-http) from loopj.
It also uses a class from a [gist](https://gist.github.com/ayvazj/6e8dcf689be7cec89579). Many thanks to ayvazj for that. 
