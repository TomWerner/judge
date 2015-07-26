package edu.uiowa.acm.judge.models;

import javax.persistence.*;

/**
 * Holds the information for a single test case of a problem.
 * We should be able to supply the input field to stdin, and expect the output field from stdout
 */
@Entity
@Table(name = "tblJDG_test_case")
public class TestCase {
    @Id
    @Column(name="test_case_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String input;

    @Column(nullable = false, length = 1000)
    private String output;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public TestCase(final String input, final String output, final Problem problem) {
        this.input = input;
        this.output = output;
        this.problem = problem;
    }

    public TestCase() {}

    public Long getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(final String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(final String output) {
        this.output = output;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(final Problem problem) {
        this.problem = problem;
    }
}
