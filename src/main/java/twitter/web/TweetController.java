package twitter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import twitter.Tweet;
import twitter.data.TweetRepository;

import java.util.Date;
import java.util.List;

/**
 * Created on 30.10.2015.
 */
@Controller
@RequestMapping("/tweets")
public class TweetController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private TweetRepository repo;

    @Autowired
    public TweetController(TweetRepository repo){
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Tweet> tweets(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
                              @RequestParam(value = "count", defaultValue = "20") int count){
        return repo.findTweets(max, count);
    }

    @RequestMapping(value = "{tweetId}", method = RequestMethod.GET)
    public String tweet(@PathVariable("tweetId") long tweetId, Model model){
        model.addAttribute(repo.findOne(tweetId));
        return "tweet";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveTweet(TweetForm form, Model model) throws Exception{
        repo.save(new Tweet(null, form.getMessage(), new Date(), form.getLatitude(), form.getLongitude()));
        return "redirect:/tweets";
    }
}
