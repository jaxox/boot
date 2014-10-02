package com.boot.web;

//@RestController
//@Controller
public class HelloController {


//
//    private Facebook facebook;
//
//    @Inject
//    public HelloController(Facebook facebook) {
//        this.facebook = facebook;
//    }

//    @RequestMapping(method= RequestMethod.GET)
//    public String helloFacebook(Model model) {
//
//
//         return  "redirect:/signin/facebook";
//
////        if (!facebook.isAuthorized()) {
////            return "redirect:/connect/facebook";
////        }
//
////
////        PagedList<?> friendLists = facebook.friendOperations().getFriendLists();
////
////
////        model.addAttribute(facebook.userOperations().getUserProfile());
////        PagedList<Post> homeFeed = facebook.feedOperations().getHomeFeed();
////        model.addAttribute("feed", homeFeed);
//
//        //return "hello";
//    }


//    @Autowired
//    AccountService accountService;

//    @RequestMapping("/")
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }


//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(value="/users", method= RequestMethod.POST)
//    public User addUser(@RequestBody User user) {
//        return accountService.createUserAccount(user);
//    }

//
//    @RequestMapping(value="/users", method= RequestMethod.POST)
//    public ResponseEntity<User> addUser(@RequestBody User user) {
//        return new ResponseEntity<>(accountService.createUserAccount(user), HttpHeaderUtils.getHeader4Json(),HttpStatus.CREATED);
//    }


}