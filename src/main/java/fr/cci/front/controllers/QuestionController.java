package fr.cci.front.controllers;

import fr.cci.front.model.QuestionModel;
import fr.cci.front.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class QuestionController {
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;

    }

    @GetMapping("/question")
    public String questionMake(Model model) {
        model.addAttribute("question", new QuestionModel());
        return "question";
    }

    @PostMapping("/question")
    public RedirectView question(@ModelAttribute QuestionModel question) {

        questionService.add(question);
        return new RedirectView("/questions");
    }

    @GetMapping("/questions")
    public ModelAndView questions() {
        ModelAndView mav = new ModelAndView("questions");
        mav.addObject("questions", questionService.get());
        return mav;
    }

    @PostMapping("/question/delete/{id}")
    public RedirectView deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return new RedirectView("/questions");
    }

    @GetMapping("/question/edit/{id}")
    public String editQuestionForm(@PathVariable Long id, Model model) {
        QuestionModel question = questionService.getById(id);
        model.addAttribute("question", question);
        return "edit_question";
    }

    @PostMapping("/question/edit")
    public RedirectView updateQuestion(@ModelAttribute QuestionModel question) {
        questionService.update(question);
        return new RedirectView("/questions");
    }

}
