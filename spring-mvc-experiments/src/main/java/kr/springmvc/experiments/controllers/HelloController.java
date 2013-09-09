package kr.springmvc.experiments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;

@Controller
public class HelloController {

    @RequestMapping(value = "/hello-page", method = RequestMethod.GET)
    public ModelAndView goToHelloPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/hello"); // name of the jsp-file in the "page" folder

        String str = "MVC Spring is here!";
        view.addObject("message", str);     // adding of str object as "message"

        return view;
    }

    @RequestMapping(value = "/async", method = RequestMethod.GET)
    @ResponseBody
    public Callable<String> asyncRun() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Async Called!!!";
            }
        };
    }
}