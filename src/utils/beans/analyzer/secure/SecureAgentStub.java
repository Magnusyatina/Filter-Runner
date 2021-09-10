package utils.beans.analyzer.secure;

public class SecureAgentStub implements SecureAgent{

    @Override
    public boolean check(String field) {
        return true;
    }

    @Override
    public void addPolicies(Policy[] policies) {

    }
}
