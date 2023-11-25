package org.genesys.synthesis;

import java.util.ArrayList;
import java.util.List;

import org.genesys.models.Example;
import org.genesys.interpreter.Interpreter;
import org.genesys.models.Node;
import org.genesys.models.Pair;
import org.genesys.type.Maybe;

/**
 * Created by utcs on 9/11/17.
 */
public class DeepCoderSimplifier {

    private final Interpreter interpreter_;
    private final List<Example> examples_;

    // constructor that takes in interpreter
    public DeepCoderSimplifier(Interpreter interpreter_2, List<Example> list) {
        interpreter_ = interpreter_2;
        examples_ = list;
    }

    public Node simplify(Node ast) {
        List<Object> ast_results = new ArrayList<>();
        for (Example example : examples_) {
            Object input = example.getInput();
            ast_results.add(interpreter_.execute(ast, input).get());
        }

        List<List<Object>> results = new ArrayList<>();
        for (int i = 0; i < ast.children.size(); i++) {
            Node node = ast.children.get(i);
            ast.children.set(i, simplify(node));
            List<Object> child_results = new ArrayList<>();
            for (Example example : examples_) {
                Object input = example.getInput();
                Object output = example.getOutput();
                child_results.add(interpreter_.execute(node, input).get());
            }
            results.add(child_results);
        }

        if (ast.function != "root") {
            for (int i = 0; i < results.size(); i++) {
                Object res = results.get(i);
                if (res.equals(ast_results)) {
                    return ast.children.get(i);
                }
            }
        }

        for (int i = 0; i < examples_.size(); i++) {
            Object input = examples_.get(i).getInput();
            Object res = interpreter_.execute(ast, input).get();
            assert interpreter_.execute(ast, input).get().equals(ast_results.get(i));
        }
        return ast;
    }
    

}
