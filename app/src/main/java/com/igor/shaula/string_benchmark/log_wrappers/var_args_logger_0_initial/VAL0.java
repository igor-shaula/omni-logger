package com.igor.shaula.string_benchmark.log_wrappers.var_args_logger_0_initial;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.igor.shaula.string_benchmark.annotations.TypeDoc;

@SuppressWarnings({"WeakerAccess", "unused"})
@TypeDoc(createdBy = "Igor Shaula", createdOn = "09-2017", modifiedOn = "11-11-2017", purpose = "" +
        "the most minimalistic & useful wrapper for local logging," +
        "helps to eliminate the 23-symbol in original TAG restriction", comment = "" +
        "every method here takes any number of arguments," +
        "the best name for this class consists of only one letter - L - for briefness in code")

public final class VAL0 {

    private static final String TAG_23 = "VariableArgsLogTag0";
    private static final String DIVIDER = " ` ";
    private static final String CONTAINER_IS_NULL = "{LogVarArgs:NULL}";
    private static final String CONTAINER_IS_EMPTY = "{LogVarArgs:EMPTY}";
    private static final String L_NULL = "{null}";
    private static final String L_EMPTY = "{empty}";
    // global constant switcher to be touched from this class only \\
    private static final boolean USE_LOGGING = true;
    // dynamic local switcher - can be helpful to toggle logging from other classes \\
    private static boolean isLogAllowed = true;

    private VAL0() {
        // should not create any instances of this class \\
    }

    public static void silence() {
        isLogAllowed = false;
    }

    public static void restore() {
        isLogAllowed = true;
    }

    // very nice & fast to write in case of using LL-like templates \\
    public static void l(@Nullable final String... strings) {
        v(strings);
    }

    public static void v(@Nullable final String... strings) {
        if (USE_LOGGING && isLogAllowed) {
            passTo(Log.VERBOSE, processAllStrings(strings));
        }
    }

    public static void d(@Nullable final String... strings) {
        if (USE_LOGGING && isLogAllowed) {
            passTo(Log.DEBUG, processAllStrings(strings));
        }
    }

    public static void i(@Nullable final String... strings) {
        if (USE_LOGGING && isLogAllowed) {
            passTo(Log.INFO, processAllStrings(strings));
        }
    }

    public static void w(@Nullable final String... strings) {
        if (USE_LOGGING && isLogAllowed) {
            passTo(Log.WARN, processAllStrings(strings));
        }
    }

    public static void e(@Nullable final String... strings) {
        if (USE_LOGGING && isLogAllowed) {
            passTo(Log.ERROR, processAllStrings(strings));
        }
    }

    public static void a(@Nullable final String... strings) {
        if (USE_LOGGING && isLogAllowed) {
            passTo(Log.ASSERT, processAllStrings(strings));
        }
    }

    // simplest and fastest - even without TAG_23 - may be used to measure speed of doing job \
    public static void f(@NonNull final String message) {
        if (USE_LOGGING && isLogAllowed) {
            System.out.println(message);
        }
    }

    private static void passTo(final int logLevel, @NonNull final String logResult) {
        if (logLevel == Log.VERBOSE) {
            Log.v(TAG_23, logResult);
        } else if (logLevel == Log.DEBUG) {
            Log.v(TAG_23, logResult);
        } else if (logLevel == Log.INFO) {
            Log.v(TAG_23, logResult);
        } else if (logLevel == Log.WARN) {
            Log.v(TAG_23, logResult);
        } else if (logLevel == Log.ERROR) {
            Log.v(TAG_23, logResult);
        } else if (logLevel == Log.ASSERT) {
            Log.wtf(TAG_23, logResult);
        } else {
            System.out.println(logResult);
        }
    }

    @NonNull
    private static String processAllStrings(@Nullable final String... strings) {
        final String logResult; // logResult = VarArgsResult \\
        if (strings == null) {
            logResult = CONTAINER_IS_NULL;
        } else if (strings.length == 0) {
            logResult = CONTAINER_IS_EMPTY;
        } else if (strings.length == 1) { // saving time by avoiding StringBuilder creation \\
            logResult = processOneString(strings[0]);
        } else { // as [strings.length cannot be < 0] -> in this case [strings.length >= 2] \\
            final int minimumCapacity = strings[0].length() + DIVIDER.length() + strings[1].length();
            // as minimum number of args here is 2 -> we're preparing StringBuilder just for it \\
            final StringBuilder logResultBuilder = new StringBuilder(minimumCapacity);
            // the starting string doesn't need the DIVIDER before it - so it's taken out of loop \\
            logResultBuilder.append(processOneString(strings[0]));
            // this loop is expected to do at least one iteration & starts from the second string \\
            for (int i = 1, argsLength = strings.length; i < argsLength; i++) {
                // as we have already wrote initial string - here we should start with DIVIDER \\
                logResultBuilder.append(DIVIDER).append(processOneString(strings[i]));
            }
            logResult = logResultBuilder.toString();
        }
        return logResult;
    }

    @NonNull
    private static String processOneString(@Nullable final String theString) {
        final String result;
        if (theString == null) {
            result = L_NULL;
        } else if (theString.isEmpty()) {
            result = L_EMPTY;
        } else {
            result = theString;
        }
        return result;
    }
}