package io.microlam.slf4j.simple;

import java.io.PrintStream;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.LambdaRuntime;
import com.amazonaws.services.lambda.runtime.LambdaRuntimeInternal;

/**
 * This class encapsulates the user's choice of output target.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
class OutputChoice {

    enum OutputChoiceType {
        SYS_OUT, CACHED_SYS_OUT, SYS_ERR, CACHED_SYS_ERR, FILE, LAMBDA;
    }

    final OutputChoiceType outputChoiceType;
    final PrintStream targetPrintStream;
    LambdaLogger lambdaLogger  = null;


    OutputChoice(OutputChoiceType outputChoiceType) {
        if (outputChoiceType == OutputChoiceType.FILE) {
            throw new IllegalArgumentException();
        }
        this.outputChoiceType = outputChoiceType;
        if (outputChoiceType == OutputChoiceType.CACHED_SYS_OUT) {
            this.targetPrintStream = System.out;
        } else if (outputChoiceType == OutputChoiceType.CACHED_SYS_ERR) {
            this.targetPrintStream = System.err;
        } else if (outputChoiceType == OutputChoiceType.LAMBDA) {
        	LambdaRuntimeInternal.setUseLog4jAppender(true);
            this.targetPrintStream = null;
            this.lambdaLogger = LambdaRuntime.getLogger();
        } else {
            this.targetPrintStream = null;
        }
    }

    OutputChoice(PrintStream printStream) {
        this.outputChoiceType = OutputChoiceType.FILE;
        this.targetPrintStream = printStream;
    }

    PrintStream getTargetPrintStream() {
        switch (outputChoiceType) {
        case SYS_OUT:
            return System.out;
        case SYS_ERR:
            return System.err;
        case CACHED_SYS_ERR:
        case CACHED_SYS_OUT:
        case LAMBDA:
        case FILE:
            return targetPrintStream;
        default:
            throw new IllegalArgumentException();
        }
    }

    boolean isLambda()  {
    	return lambdaLogger != null;
    }

    LambdaLogger getLambdaLogger() {
    	return lambdaLogger;
    }
 
}
