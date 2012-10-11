package org.incava.diffj;

import net.sourceforge.pmd.ast.ASTFormalParameter;
import net.sourceforge.pmd.ast.Token;
import org.incava.pmdx.ParameterUtil;

public class Parameter {
    private final ASTFormalParameter param;
    
    public Parameter(ASTFormalParameter param) {
        this.param = param;
    }

    protected Token getParameterName() {
        return ParameterUtil.getParameterName(param);
    }

    protected String getParameterType() {
        return ParameterUtil.getParameterType(param);
    }
}
