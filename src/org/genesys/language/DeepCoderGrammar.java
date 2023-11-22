package org.genesys.language;

import org.genesys.models.Example;
import org.genesys.models.Problem;
import org.genesys.type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufeng on 5/31/17.
 * Grammar for deepCoder.
 */
public class DeepCoderGrammar implements Grammar<AbstractType> {

    private int id = 1;

    private int typeDist = 5;

    public AbstractType inputType;

    public AbstractType outputType;

    private List<InputType> inputTypes = new ArrayList<>();

    public DeepCoderGrammar(AbstractType inputType, AbstractType outputType) {
        this.inputType = inputType;
        inputTypes.add(new InputType(0, inputType));
        this.outputType = outputType;
    }

    public DeepCoderGrammar(Problem p) {
        assert !p.getExamples().isEmpty();
        Example example = p.getExamples().get(0);
        List input = example.getInput();
        for (int i = 0; i < input.size(); i++) {
            Object elem = input.get(i);
            InputType in;
            in = new InputType(i, getType(elem));
            // if (elem instanceof List)
            //     in = new InputType(i, new ListType(new IntType()));
            // else
            //     in = new InputType(i, new IntType());

        /* dynamically add input to grammar. */
            addInput(in);
        }
        Object output = example.getOutput();
        this.outputType = getType(output);
        System.out.println("inputTypes: " + this.inputTypes);
        System.out.println("outputType: " + this.outputType);
    }

    private AbstractType getType(Object obj) {
        if (obj instanceof List) {
            // if no element throw error
            if (((List) obj).isEmpty()) {
                throw new RuntimeException("Empty list");
            }
            return new ListType(getType((Object)((List)obj).toArray()[0]));
        } else {
            return new IntType();
        }
    }

    public void addInput(InputType in) {
        inputTypes.add(in);
    }

    @Override
    public AbstractType start() {
        return new InitType(this.outputType);
    }

    @Override
    public String getName() {
        return "DeepCoderGrammar";
    }

    public AbstractType getOutputType() {
        return this.outputType;
    }

    @Override
    public List<Production<AbstractType>> getLineProductions(int size) {
        List<Production<AbstractType>> productions = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < typeDist; j++) {
                productions.add(new Production<>(creatType(j), "line" + i + "depth" + j));
            }
            // productions.add(new Production<>(new IntType(), "line" + i + "int"));
            // productions.add(new Production<>(new ListType(new IntType()), "line" + i + "list"));
            // productions.add(new Production<>(new ListType(new ListType(new IntType())), "line" + i + "listlist"));
        }

        return productions;
    }

    private AbstractType creatType(int i) {
        assert (i >= 0);
        if (i == 0) {
            return new IntType();
        } 
        return new ListType(creatType(i - 1));
    }

    @Override
    public List<Production<AbstractType>> getInputProductions() {
        List<Production<AbstractType>> productions = new ArrayList<>();

        for (InputType input : inputTypes) {
            assert (input.getType() instanceof IntType || input.getType() instanceof ListType || input.getType() instanceof BoolType);
            productions.add(new Production<>(input.getType(), "input" + input.getIndex()));
            // if (input.getType() instanceof IntType)
            //     productions.add(new Production<>(new IntType(), "input" + input.getIndex()));
            // else if (input.getType() instanceof ListType)
            //     productions.add(new Production<>(input.getType(), "input" + input.getIndex()));
            // else if (input.getType() instanceof BoolType)
            //     productions.add(new Production<>(new BoolType(), "input" + input.getIndex()));
            // else
            //     assert (false);
        }

        return productions;
    }


    @Override
    public List<Production<AbstractType>> getProductions() {
        List<Production<AbstractType>> productions = new ArrayList<>();
        // IntType
        productions.add(new Production<>(new ConstType(),"-3"));
        productions.add(new Production<>(new ConstType(),"-2"));
        productions.add(new Production<>(new ConstType(),"-1"));
        productions.add(new Production<>(new ConstType(),"0"));
        productions.add(new Production<>(new ConstType(),"1"));
        productions.add(new Production<>(new ConstType(),"2"));
        productions.add(new Production<>(new ConstType(),"3"));

        // productions.add(new Production<>(new ConstNotZeroType(),"-3"));
        // productions.add(new Production<>(new ConstNotZeroType(),"-2"));
        // productions.add(new Production<>(new ConstNotZeroType(),"-1"));
        // productions.add(new Production<>(new ConstNotZeroType(),"1"));
        // productions.add(new Production<>(new ConstNotZeroType(),"2"));
        // productions.add(new Production<>(new ConstNotZeroType(),"3"));

        // productions.add(new Production<>(new ConstPosType(),"2"));
        // productions.add(new Production<>(new ConstPosType(),"3"));

        productions.add(new Production<>(true, true, id++, new IntType(), "MAXIMUM", new ListType(new IntType())));
        productions.add(new Production<>(false, true,id++,new IntType(), "COUNT", new ListType(new IntType()), new BinopBoolType(),new ConstNotZeroType()));
        productions.add(new Production<>(true, true, id++, new IntType(), "MINIMUM", new ListType(new IntType())));
        productions.add(new Production<>(true, true, id++,new IntType(), "SUM", new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new TemplateType(), "HEAD", new ListType(new TemplateType())));
        productions.add(new Production<>(true, true,id++,new TemplateType(), "LAST", new ListType(new TemplateType())));
        productions.add(new Production<>(false, true,id++,new TemplateType(), "ACCESS", new ListType(new TemplateType()), new IntType()));

        // ListType
        // productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "MAP-MUL", new ListType(new IntType()), new ConstType()));
        // productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "MAP-DIV", new ListType(new IntType()), new ConstNotZeroType()));
        // productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "MAP-PLUS", new ListType(new IntType()), new ConstType()));
        // productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "MAP-POW", new ListType(new IntType()), new ConstPosType()));

        // added new productions
        productions.add(new Production<>(false, true,id++,new ListType(new TemplateType()), "MAP-UNARY", new UnopType(), new ListType(new ListType(new TemplateType()))));
        productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "MAP-BINARY", new BinopIntType(), new ConstType(), new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new ListType(new ListType(new TemplateType())), "GROUP", new ListType(new TemplateType())));

        productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "FILTER", new ListType(new IntType()), new BinopBoolType(),new ConstType()));

        productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "ZIPWITH", new ListType(new IntType()), new ListType(new IntType()), new BinopIntType()));
        // productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "ZIPWITH-PLUS", new ListType(new IntType()), new ListType(new IntType())));
        // productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "ZIPWITH-MINUS", new ListType(new IntType()), new ListType(new IntType())));
        // productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "ZIPWITH-MUL", new ListType(new IntType()), new ListType(new IntType())));
        // productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "ZIPWITH-MIN", new ListType(new IntType()), new ListType(new IntType())));
        // productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "ZIPWITH-MAX", new ListType(new IntType()), new ListType(new IntType())));

        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "SORT", new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "REVERSE", new ListType(new IntType())));

        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "SCANL1-PLUS", new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "SCANL1-MINUS", new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "SCANL1-MUL", new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "SCANL1-MIN", new ListType(new IntType())));
        productions.add(new Production<>(true, true,id++,new ListType(new IntType()), "SCANL1-MAX", new ListType(new IntType())));

        productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "TAKE", new ListType(new IntType()), new IntType()));
        productions.add(new Production<>(false, true,id++,new ListType(new IntType()), "DROP", new ListType(new IntType()), new IntType()));

        //FunctionType
        productions.add(new Production<>(new BinopBoolType(), "l(a,b).(> a b)"));
        productions.add(new Production<>(new BinopBoolType(), "l(a,b).(< a b)"));
        productions.add(new Production<>(new BinopBoolType(), "l(a,b).(== a b)"));
        productions.add(new Production<>(new BinopBoolType(), "l(a,b).(!= a b)"));
        productions.add(new Production<>(new BinopBoolType(), "l(a,b).(%!= a b)"));
        productions.add(new Production<>(new BinopBoolType(), "l(a,b).(%= a b)"));

        // plus mul div pow
        productions.add(new Production<>(new BinopIntType(), "+"));
        // productions.add(new Production<>(new BinopIntType(), "-"));
        productions.add(new Production<>(new BinopIntType(), "*"));
        productions.add(new Production<>(new BinopIntType(), "/"));
        productions.add(new Production<>(new BinopIntType(), "^"));

        productions.add(new Production<>(new BinopIntType(), "MIN"));
        productions.add(new Production<>(new BinopIntType(), "MAX"));

        // iterate over all production rules and check if it is higher
        List<Production<AbstractType>> secondary_productions = new ArrayList<>();
        for (Production<AbstractType> production : productions) {
            if (production.canBeInput) {
                // assert number of inputs is 1
                if (production.inputs.length == 1) {
                    secondary_productions.add(new Production<>(new UnopType(), production.function + "_FUNCTION"));
                }
            }
        }
        productions.addAll(secondary_productions);
        return productions;
    }

    @Override
    public List<Production<AbstractType>> productionsFor(AbstractType symbol) {
        List<Production<AbstractType>> productions = new ArrayList<>();
        if (symbol instanceof InitType) {
            InitType type = (InitType) symbol;
            productions.add(new Production<>(symbol, "root", type.goalType));
        } else if (symbol instanceof IntType) {
            productions.add(new Production<>(new IntType(), "MAXIMUM", new ListType(new IntType())));
            productions.add(new Production<>(new IntType(), "MINIMUM", new ListType(new IntType())));
            productions.add(new Production<>(new IntType(), "SUM", new ListType(new IntType())));
            productions.add(new Production<>(new IntType(), "HEAD", new ListType(new IntType())));
            productions.add(new Production<>(new IntType(), "LAST", new ListType(new IntType())));
            productions.add(new Production<>(new IntType(), "COUNT", new ListType(new IntType()), new FunctionType(new IntType(), new BoolType())));
            productions.add(new Production<>(new IntType(), "ACCESS", new ListType(new IntType()), new IntType()));

        } else if (symbol instanceof ListType) {
            // ListType -- only considering lists of IntType
            productions.add(new Production<>(new ListType(new IntType()), "FILTER", new ListType(new IntType()), new FunctionType(new IntType(), new BoolType())
            ));
            productions.add(new Production<>(new ListType(new IntType()), "MAP", new ListType(new IntType()), new FunctionType(new IntType(), new IntType())
            ));
            productions.add(new Production<>(new ListType(new IntType()), "ZIPWITH",
                    new ListType(new IntType()), new ListType(new IntType()), new FunctionType(new PairType(new IntType(), new IntType()), new IntType())));
            productions.add(new Production<>(new ListType(new IntType()), "SORT", new ListType(new IntType())));
            productions.add(new Production<>(new ListType(new IntType()), "REVERSE", new ListType(new IntType())));
            productions.add(new Production<>(new ListType(new IntType()), "SCANL1", new FunctionType(new PairType(new IntType(),
                    new IntType()), new IntType()), new ListType(new IntType())));


            productions.add(new Production<>(new ListType(new IntType()), "TAKE", new ListType(new IntType()), new IntType()));
            productions.add(new Production<>(new ListType(new IntType()), "DROP", new ListType(new IntType()), new IntType()));
        } else if (symbol instanceof FunctionType) {
            FunctionType type = (FunctionType) symbol;
            if ((type.inputType instanceof IntType) && (type.outputType instanceof BoolType)) {
                productions.add(new Production<>(new FunctionType(new IntType(), new BoolType()), "isPOS", new IntType()));
                productions.add(new Production<>(new FunctionType(new IntType(), new BoolType()), "isNEG", new IntType()));
                productions.add(new Production<>(new FunctionType(new IntType(), new BoolType()), "isODD", new IntType()));
                productions.add(new Production<>(new FunctionType(new IntType(), new BoolType()), "isEVEN", new IntType()));
            }

            if ((type.inputType instanceof IntType) && (type.outputType instanceof IntType)) {
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "MUL3"));
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "MUL4"));
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "DIV3"));
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "DIV4"));

                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "INC"));
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "DEC"));

                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "SHL"));
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "SHR"));

                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "SQR"));
                productions.add(new Production<>(new FunctionType(new IntType(), new IntType()), "doNEG"));

            }

            if (type.inputType instanceof PairType) {
                //FunctionType
                productions.add(new Production<>(new FunctionType(new PairType(new IntType(), new IntType()), new IntType()), "+"));
                productions.add(new Production<>(new FunctionType(new PairType(new IntType(), new IntType()), new IntType()), "-"));
                productions.add(new Production<>(new FunctionType(new PairType(new IntType(), new IntType()), new IntType()), "*"));

                productions.add(new Production<>(new FunctionType(new PairType(new IntType(), new IntType()), new IntType()), "MIN"));
                productions.add(new Production<>(new FunctionType(new PairType(new IntType(), new IntType()), new IntType()), "MAX"));
            }
        }

        /* Handle inputs */
        for (InputType input : inputTypes) {
            boolean flag1 = (symbol instanceof IntType) && (input.getType() instanceof IntType);
            boolean flag2 = (symbol instanceof ListType) && (input.getType() instanceof ListType);
            if (flag1 || flag2) {
                productions.add(new Production<>(symbol, "input" + input.getIndex()));
            }
        }
        return productions;
    }

    @Override
    public List<AbstractType> getAllTypes(AbstractType type) {
        List<AbstractType> types = new ArrayList<>();
        if (type instanceof TemplateType) {
            for (int i = 0; i < typeDist; i++) {
                types.add(creatType(i));
            }
        }
        else if (type instanceof ListType) {
            List<AbstractType> innerTypes = getAllTypes(((ListType) type).type);
            for (AbstractType innerType : innerTypes) {
                types.add(new ListType(innerType));
            }
        }
        else {
            types.add(type);
        }
        return types;
    }
}
