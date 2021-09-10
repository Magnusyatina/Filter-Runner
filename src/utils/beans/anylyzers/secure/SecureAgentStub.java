package utils.beans.anylyzers.secure;

public class SecureAgentStub implements SecureAgent{

    @Override
    public boolean check(String field) {
        return true;
    }

    @Override
    public void addPolicies(Policy[] policies) {

    }
}
