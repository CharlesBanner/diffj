package org.incava.diffj.function;

import java.util.Iterator;
import java.util.List;
import net.sourceforge.pmd.ast.ASTBlockStatement;
import net.sourceforge.pmd.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.ast.ASTFormalParameters;
import net.sourceforge.pmd.ast.ASTNameList;
import net.sourceforge.pmd.ast.JavaParserConstants;
import net.sourceforge.pmd.ast.Token;
import org.incava.diffj.code.Code;
import org.incava.diffj.code.Statement;
import org.incava.diffj.code.TokenList;
import org.incava.diffj.element.Diffable;
import org.incava.diffj.element.Differences;
import org.incava.diffj.params.Parameters;
import org.incava.diffj.util.Messages;
import org.incava.ijdk.text.Message;
import org.incava.pmdx.CtorUtil;
import org.incava.pmdx.SimpleNodeUtil;

public class Ctor extends Function implements Diffable<Ctor> {
    public final static Message CONSTRUCTOR_REMOVED = new Message("constructor removed: {0}");
    public final static Message CONSTRUCTOR_ADDED = new Message("constructor added: {0}");
    public final static Messages CTOR_MESSAGES = new Messages(CONSTRUCTOR_ADDED, null, CONSTRUCTOR_REMOVED);

    private final ASTConstructorDeclaration ctor;

    public Ctor(ASTConstructorDeclaration ctor) {
        super(ctor);
        this.ctor = ctor;
    }

    public void diff(Ctor toCtor, Differences differences) {
        compareAccess(toCtor, differences);
        compareParameters(toCtor, differences);
        compareThrows(toCtor, differences);
        compareCode(toCtor, differences);
    }

    protected ASTFormalParameters getFormalParameters() {
        return CtorUtil.getParameters(ctor);
    }

    protected ASTNameList getThrowsList() {
        return CtorUtil.getThrowsList(ctor);
    }

    public String getName() {
        return CtorUtil.getFullName(ctor);
    }    

    public void compareCode(Ctor toCtor, Differences differences) {
        Code fromCode = new Code(getName(), getCodeTokens());
        Code toCode = new Code(toCtor.getName(), toCtor.getCodeTokens());
        fromCode.diff(toCode, differences);
    }

    protected TokenList getCodeTokens() {
        List<ASTBlockStatement> stmts = SimpleNodeUtil.findChildren(ctor, ASTBlockStatement.class);
        List<Token> tokens = new java.util.ArrayList<Token>();
        for (ASTBlockStatement blkStmt : stmts) {
            Statement stmt = new Statement(blkStmt);
            tokens.addAll(stmt.getTokens());
        }
        return new TokenList(tokens);
    }

    public double getMatchScore(Ctor toCtor) {
        Parameters fromParams = getParameters();
        Parameters toParams = toCtor.getParameters();
        return fromParams.getMatchScore(toParams);
    }

    public Message getAddedMessage() {
        return CTOR_MESSAGES.getAdded();
    }

    public Message getRemovedMessage() {
        return CTOR_MESSAGES.getDeleted();
    }
}
