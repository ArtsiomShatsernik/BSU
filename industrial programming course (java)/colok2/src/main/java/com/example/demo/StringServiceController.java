package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StringServiceController {

    @RequestMapping("/threeTimes")
    public StringService threeTime(@RequestParam(value = "string1") String string1) {
        StringService serv = new StringService(string1);
        serv.threeString();
        return serv;
    }
   @RequestMapping("/stringCon")
    public StringService stringCon(@RequestParam(value = "string1" ) String string1,
                                   @RequestParam(value = "string2" ) String string2 ) {
        StringService serv = new StringService(string1);
        serv.stringCon(string2);
        return serv;
   }

}
