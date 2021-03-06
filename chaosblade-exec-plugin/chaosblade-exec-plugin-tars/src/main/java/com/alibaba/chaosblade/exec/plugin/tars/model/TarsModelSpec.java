package com.alibaba.chaosblade.exec.plugin.tars.model;


import com.alibaba.chaosblade.exec.common.aop.PredicateResult;
import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.Model;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;
import com.alibaba.chaosblade.exec.plugin.tars.TarsConstant;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author saikei
 * @email lishiji@huya.com
 */
public class TarsModelSpec extends FrameworkModelSpec{

    @Override
    protected List<MatcherSpec> createNewMatcherSpecs() {
        ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
        matcherSpecs.add(new ClientMatcherSpec());
        matcherSpecs.add(new ServantMatcherSpec());
        matcherSpecs.add(new ServantNameMatcherSpec());
        matcherSpecs.add(new FunctionNameMatcherSpec());
        return matcherSpecs;
    }

    @Override
    public String getTarget() {
        return TarsConstant.TARGET_NAME;
    }

    @Override
    public String getShortDesc() {
        return "tars experiment";
    }

    @Override
    public String getLongDesc() {
        return "Tars experiment for testing service delay and exception.";
    }

    @Override
    protected PredicateResult preMatcherPredicate(Model model) {
        if (model == null) {
            return PredicateResult.fail("matcher not found for tars");
        }
        MatcherModel matcher = model.getMatcher();
        Set<String> keySet = matcher.getMatchers().keySet();
        for (String key : keySet) {
            if (key.equals(TarsConstant.CLIENT) || key.equals(TarsConstant.SERVANT)) {
                return PredicateResult.success();
            }
        }
        return PredicateResult.fail("less necessary matcher is client or servant for tars");
    }

    @Override
    public String getExample() {
        return "tars delay --time 3000 --client --servantname app.server.obj --functionname hello";
    }

}
