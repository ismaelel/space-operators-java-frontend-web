package fr.cci.front.controllers;

import fr.cci.front.model.QuestionModel;
import fr.cci.front.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Contrôleur Spring MVC gérant les opérations CRUD sur les questions.
 * <p>
 * Permet de créer, afficher, modifier et supprimer des questions via des requêtes HTTP.
 * </p>
 */
@Controller
public class QuestionController {
    private QuestionService questionService;

    /**
     * Constructeur avec injection du service QuestionService.
     *
     * @param questionService le service de gestion des questions
     */
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Affiche le formulaire pour créer une nouvelle question.
     *
     * @param model modèle pour la vue, contenant une instance vide de QuestionModel
     * @return nom de la vue du formulaire de création
     */
    @GetMapping("/question")
    public String questionMake(Model model) {
        model.addAttribute("question", new QuestionModel());
        return "question/question";
    }

    /**
     * Traite la soumission du formulaire de création d'une question.
     *
     * @param question objet QuestionModel soumis par le formulaire
     * @return redirection vers la liste des questions
     */
    @PostMapping("/question")
    public RedirectView question(@ModelAttribute QuestionModel question) {
        questionService.add(question);
        return new RedirectView("/questions");
    }

    /**
     * Affiche la liste de toutes les questions.
     *
     * @return ModelAndView pour la vue listant les questions, avec l'attribut "questions"
     */
    @GetMapping("/questions")
    public ModelAndView questions() {
        ModelAndView mav = new ModelAndView("question/questions");
        mav.addObject("questions", questionService.get());
        return mav;
    }

    /**
     * Supprime une question identifiée par son ID.
     *
     * @param id identifiant de la question à supprimer
     * @return redirection vers la liste des questions
     */
    @PostMapping("/question/delete/{id}")
    public RedirectView deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return new RedirectView("/questions");
    }

    /**
     * Affiche le formulaire de modification d'une question existante.
     *
     * @param id    identifiant de la question à modifier
     * @param model modèle pour la vue, contenant la question récupérée
     * @return nom de la vue du formulaire d'édition
     */
    @GetMapping("/question/edit/{id}")
    public String editQuestionForm(@PathVariable Long id, Model model) {
        QuestionModel question = questionService.getById(id);
        model.addAttribute("question", question);
        return "question/edit_question";
    }

    /**
     * Traite la soumission du formulaire de modification d'une question.
     *
     * @param question objet QuestionModel modifié soumis par le formulaire
     * @return redirection vers la liste des questions
     */
    @PostMapping("/question/edit")
    public RedirectView updateQuestion(@ModelAttribute QuestionModel question) {
        questionService.update(question);
        return new RedirectView("/questions");
    }
}
