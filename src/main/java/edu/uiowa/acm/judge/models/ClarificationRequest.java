package edu.uiowa.acm.judge.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tom on 6/27/2015.
 */
@Entity
@Table(name = "tblJDG_clarification_request")
public class ClarificationRequest implements Serializable {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column
    private String requestContent;

    public ClarificationRequest(final Problem problem, final String requestContent) {
        super();
        this.problem = problem;
        this.requestContent = requestContent;
    }

    public Long getId() {
        return id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(final Problem problem) {
        this.problem = problem;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(final String requestContent) {
        this.requestContent = requestContent;
    }
}
