package org.genesys.synthesis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.genesys.models.Example;
import org.genesys.interpreter.Interpreter;
import org.genesys.models.Node;
import org.genesys.models.Pair;

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
        return simplify_helper(ast).t0;
    }

    private Pair<Node, List<HashMap<Object, Set<Node>>>> simplify_helper(Node ast) {
        List<Object> ast_results = new ArrayList<>();
        for (Example example : examples_) {
            Object input = example.getInput();
            ast_results.add(interpreter_.execute(ast, input).get());
        }

        List<List<HashMap<Object, Set<Node>>>> children_results = new ArrayList<>();
        for (int i = 0; i < ast.children.size(); i++) {
            Node node = ast.children.get(i);
            Pair<Node, List<HashMap<Object, Set<Node>>>> pair = simplify_helper(node);
            ast.children.set(i, pair.t0);
            children_results.add(pair.t1);
        }

        // special simplification for COUNT
        // if result is always the same across all examples, then replace ast with result
        if (ast.function.equals("COUNT") && examples_.size() > 1) {
            Integer result = (Integer) ast_results.get(0);
            boolean same = true;
            for (int i = 1; i < ast_results.size(); i++) {
                if (!ast_results.get(i).equals(result)) {
                    same = false;
                    break;
                }
            }

            if (same) {
                Node new_ast = new Node(result.toString(), new ArrayList<>());
                List<HashMap<Object, Set<Node>>> list_hashmap = new ArrayList<>();
                // add result to each one
                for (int i = 0; i < examples_.size(); i++) {
                    Object result_ = ast_results.get(i);
                    HashMap<Object, Set<Node>> map_ = new HashMap<>();
                    map_.put(result_, new HashSet<>());
                    map_.get(result_).add(new_ast);
                    list_hashmap.add(map_);
                }
                return new Pair<>(new_ast, list_hashmap);
            }
        }

        Node new_ast = ast;
        if (new_ast.function != "root") {
            for (int i = 0; i < new_ast.children.size(); i++) {
                List<HashMap<Object, Set<Node>>> child_result = children_results.get(i);
                Set<Node> same_result = new HashSet<>();
                for (int j = 0; j < examples_.size(); j++) {
                    if (child_result.get(j).containsKey(ast_results.get(j))) {
                        if (same_result.isEmpty()) {
                            same_result.addAll(child_result.get(j).get(ast_results.get(j)));
                        } else {
                            same_result.retainAll(child_result.get(j).get(ast_results.get(j)));
                        }
                    }
                    else {
                        same_result.clear();
                        break;
                    }
                }

                if (!same_result.isEmpty()) {
                    // iterate through same_result and find the one with the smallest size to replace new_ast with
                    int min_size = Integer.MAX_VALUE;
                    Node min_node = null;
                    for (Node node : same_result) {
                        if (node.size() < min_size) {
                            min_size = node.size();
                            min_node = node;
                        }
                    }
                    new_ast = min_node;
                    return new Pair<>(new_ast, child_result);
                }
            }
        }

        // construct hashmap for new_ast
        List<HashMap<Object, Set<Node>>> list_hashmap = new ArrayList<>();
        // combine all children's hashmaps
        for (int i = 0; i < examples_.size(); i++) {
            HashMap<Object, Set<Node>> child_map = new HashMap<>();
            for (int j = 0; j < ast.children.size(); j++) {
                HashMap<Object, Set<Node>> child_result = children_results.get(j).get(i);
                for (Object key : child_result.keySet()) {
                    if (!child_map.containsKey(key)) {
                        child_map.put(key, new HashSet<>());
                    }
                    child_map.get(key).addAll(child_result.get(key));
                }
            }
            list_hashmap.add(child_map);
        }
        // add new_ast result to hashmap
        for (int i = 0; i < examples_.size(); i++) {
            Object result = ast_results.get(i);
            if (!list_hashmap.get(i).containsKey(result)) {
                list_hashmap.get(i).put(result, new HashSet<>());
            }
            list_hashmap.get(i).get(result).add(new_ast);
        }
        return new Pair<>(new_ast, list_hashmap);
    }
    

}
