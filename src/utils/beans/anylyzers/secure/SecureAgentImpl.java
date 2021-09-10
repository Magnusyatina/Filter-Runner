package utils.beans.anylyzers.secure;

import java.util.LinkedList;
import java.util.List;

public class SecureAgentImpl implements SecureAgent {

    List<Policy> policies = new LinkedList<>();

    @Override
    public boolean check(String field) {
        for (Policy policy : policies) {
            if (policy.getPolicyIdentifier().equals(field)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addPolicies(Policy[] policies) {
        for (Policy element : policies) {
            this.policies.add(element);
        }
    }
}