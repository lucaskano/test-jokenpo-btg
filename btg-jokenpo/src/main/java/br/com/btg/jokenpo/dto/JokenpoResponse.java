package br.com.btg.jokenpo.dto;

import java.util.List;
import java.util.Objects;

public class JokenpoResponse {

    private String matchResult;

    private List<String> history;

    public JokenpoResponse(String matchResult, List<String> history) {
        this.matchResult = matchResult;
        this.history = history;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JokenpoResponse that = (JokenpoResponse) o;
        return Objects.equals(matchResult, that.matchResult) &&
                Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchResult, history);
    }

    @Override
    public String toString() {
        return "JokenpoResponse{" +
                "matchResult='" + matchResult + '\'' +
                ", history=" + history +
                '}';
    }
}
