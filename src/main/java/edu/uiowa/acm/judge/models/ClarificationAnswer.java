package edu.uiowa.acm.judge.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tom on 6/27/2015.
 */
@Entity
@Table(name = "tblJDG_clarification_answer")
public class ClarificationAnswer implements Serializable {
    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private ClarificationRequest request;

    @Column
    private String answerContent;

    public ClarificationAnswer(final Problem problem,
                               final ClarificationRequest request,
                               final String answerContent) {
        super();
        this.problem = problem;
        this.request = request;
        this.answerContent = answerContent;
    }

    public Long getId() {
        return id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(final String answerContent) {
        this.answerContent = answerContent;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(final Problem problem) {
        this.problem = problem;
    }

    public ClarificationRequest getRequest() {
        return request;
    }

    public void setRequest(final ClarificationRequest request) {
        this.request = request;
    }
}
