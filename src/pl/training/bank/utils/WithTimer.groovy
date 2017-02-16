package pl.training.bank.utils

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
class WithTimer extends AbstractASTTransformation {

    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        List methods = sourceUnit.getAST()?.getMethods()
        // find all methods annotated with @Timer
        methods.findAll { MethodNode method ->

            method.getAnnotations(new ClassNode(Timer))
        }.each { MethodNode method ->
            String startTimeVarName = "startTime"
            String endTimeVarName = "endTime"
            String totalTimeVarName = "totalTime"

            Statement initialTime = getInitialTimeFromString(startTimeVarName)
            Statement endTime = createSystemNanoTimeAst(endTimeVarName)
            Statement executionTime = createExecutionTimeAst(totalTimeVarName, startTimeVarName, endTimeVarName)
            Statement printTime = createPrintlnAst(totalTimeVarName)

            List existingStatements = method.getCode().getStatements()
            existingStatements.add(0, initialTime)
            existingStatements.add(endTime)
            existingStatements.add(executionTime)
            existingStatements.add(printTime)
        }
    }

    private Statement createSystemNanoTimeAst(String var) {
        return new ExpressionStatement(
                new DeclarationExpression(
                        new VariableExpression(var),
                        Token.newSymbol(Types.EQUALS, 0, 0),
                        new MethodCallExpression(
                                new ClassExpression(new ClassNode(System)),
                                "nanoTime",
                                MethodCallExpression.NO_ARGUMENTS
                        )
                )
        )
    }

    private ExpressionStatement getInitialTimeFromString(String varName) {
        List<ASTNode> nodes = new AstBuilder().buildFromString("def ${varName} = System.nanoTime()")

        BlockStatement block = (BlockStatement) nodes.get(0)
        List<Statement> statements = block.getStatements()
        if (statements != null && statements.size() > 0) {
            return new ExpressionStatement(statements.get(0).expression)
        }

        return null
    }

    private Statement createExecutionTimeAst(String totalTimeVarName, String startTimeVarName, String endTimeVarName) {
        return new ExpressionStatement(
                new DeclarationExpression(
                        new VariableExpression(totalTimeVarName),
                        Token.newSymbol(Types.EQUALS, 0, 0),
                        new BinaryExpression(
                                new BinaryExpression(
                                        new VariableExpression(endTimeVarName),
                                        Token.newSymbol(Types.MINUS, 0, 0),
                                        new VariableExpression(startTimeVarName),
                                ),
                                Token.newSymbol(Types.DIVIDE, 0, 0),
                                new ConstantExpression(new Integer(1000000))
                        )
                )
        )
    }

    private Statement createPrintlnAst(String message) {
        return new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression("this"),
                        new ConstantExpression("println"),
                        new ArgumentListExpression(
                                new VariableExpression(message)
                        )
                )
        )
    }

}

