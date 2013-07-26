package kr.springmvc.experiments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
}