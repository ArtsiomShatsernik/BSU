package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StringServiceController {

    @RequestMapping("/multiString")
    public StringService multiString(@RequestParam(value = "string") String string1,
                                     @RequestParam(value = "times") int times) {
        StringService serv = new StringService(string1);
        serv.multiString(times);
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
