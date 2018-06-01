package com.igor.shaula.string_benchmark.screens.mandatory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.igor.shaula.string_benchmark.BuildConfig;
import com.igor.shaula.string_benchmark.R;
import com.igor.shaula.string_benchmark.screens.for_ui.SimpleTextWatcher;
import com.igor.shaula.string_benchmark.utils.C;
import com.igor.shaula.string_benchmark.utils.L;
import com.igor.shaula.string_benchmark.utils.U;

public final class MainUi implements MainHub.UiLink, View.OnClickListener {

    private static final String CN = "MainUi";
    @NonNull
    private final Context rootContext;
    @NonNull
    private final View rootView;

    @SuppressWarnings("NullableProblems") // invoked in the Logic's constructor \\
    @NonNull
    private MainHub.LogicLink logicLink;

    private ProgressDialog pdWait;
    private ProgressBar pbViewCreatedBurden;

    // preparation of the burden \\
    private TextView tvStartingExplanation;
    private TextInputLayout tilBasicString;
    private EditText etBasicString;
    private TextInputLayout tilStringsAmount;
    private EditText etStringsAmount;
    private Button bPrepareBurden;
    private Button bViewBurden;
    private TextView tvResultOfPreparation;

    // iterations \\
    private TextInputLayout tilIterationsAmount;
    private EditText etIterationsAmount;
    private Button bToggleIterations;
    private Button bViewAllResults;
    private TextView tvBurdenExplanation;
    private TextView tvResultForSout;
    private TextView tvResultForLog;
    private TextView tvResultForDAL;
    private TextView tvResultForVAL1;
    private TextView tvResultForVAL2;
    private TextView tvResultForVAL3;
    private TextView tvResultForSLVoid;
    private TextView tvResultForSLInt;

    MainUi(@NonNull View rootView) {
        this.rootView = rootView;
        rootContext = rootView.getContext();
    }

    @NonNull
    @Override
    public String getBasicStringText() {
        return etBasicString.getText().toString();
    }

    @NonNull
    @Override
    public String getStringsAmountText() {
        return etStringsAmount.getText().toString();
    }

    @NonNull
    @Override
    public String getIterationsAmountText() {
        return etIterationsAmount.getText().toString();
    }

    @Override
    public void setLogicLink(@NonNull MainHub.LogicLink logicLink) {
        this.logicLink = logicLink;
    }

    @Override
    public void setInitialInputFieldsValues() {
        etBasicString.setText(C.INITIAL_BASIC_STRING);
        etBasicString.setSelection(C.INITIAL_BASIC_STRING.length());
        etStringsAmount.setText(C.INITIAL_STRING_REPETITIONS);
        etStringsAmount.setSelection(C.INITIAL_STRING_REPETITIONS.length());
        etIterationsAmount.setText(C.INITIAL_TEST_ITERATIONS);
        etIterationsAmount.setSelection(C.INITIAL_TEST_ITERATIONS.length());
    }

    // currently not used \\
    @Override
    public void setBusy(boolean isBusy) {
        L.i(CN, "setBusy = " + isBusy);
        if (isBusy) {
            pdWait.show();
        } else if (!((Activity) rootContext).isFinishing()
                && pdWait != null
                && pdWait.isShowing()) {
            try {
                pdWait.dismiss();
            } catch (IllegalArgumentException iae) {
                L.e(CN, iae.getMessage());
            }
        }
    }

    @Override
    public void toggleViewBurdenBusyStateOnMainThread(final boolean isBusy) {
        rootView.post(new Runnable() {
            @Override
            public void run() {
//        bViewAllResults.setVisibility(isBusy ? View.GONE : View.VISIBLE);
                pbViewCreatedBurden.setVisibility(isBusy ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void updatePreparationsResultOnMainThread(@NonNull final long[] oneIterationResults) {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                tvResultForSout.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_SOUT]));
                tvResultForLog.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_LOG]));
                tvResultForDAL.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_DAL]));
                tvResultForVAL1.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_VAL_1]));
                tvResultForVAL2.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_VAL_2]));
                tvResultForVAL3.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_VAL_3]));
                tvResultForSLVoid.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_SL_VOID]));
                tvResultForSLInt.setText(U.adaptForUser(rootContext, oneIterationResults[C.Order.INDEX_OF_SL_INT]));
            }
        });
    }

    @Override
    public void toggleJobActiveUiState(boolean isJobRunning) {
        etBasicString.setEnabled(!isJobRunning);
        etStringsAmount.setEnabled(!isJobRunning);
        etIterationsAmount.setEnabled(!isJobRunning);
        bPrepareBurden.setEnabled(!isJobRunning);
        bViewBurden.setEnabled(!isJobRunning && logicLink.isBurdenReady());
//        pbViewCreatedBurden.invalidate();
//        toggleViewBurdenBusyStateOnMainThread(!isJobRunning && logicLink.isBurdenReady());
        toggleViewBurdenBusyStateOnMainThread(bViewBurden.isEnabled());
        // TODO: 05.12.2017 solve the problem of disappearing progressBar \\
    }

    @Override
    public void resetResultViewStates() {
        tvResultForSout.setText(String.valueOf(C.STAR));
        tvResultForLog.setText(String.valueOf(C.STAR));
        tvResultForDAL.setText(String.valueOf(C.STAR));
        tvResultForVAL1.setText(String.valueOf(C.STAR));
        tvResultForVAL2.setText(String.valueOf(C.STAR));
        tvResultForVAL3.setText(String.valueOf(C.STAR));
        tvResultForSLVoid.setText(String.valueOf(C.STAR));
        tvResultForSLInt.setText(String.valueOf(C.STAR));
    }

    @Override
    public void resetResultOfPreparation() {
        tvResultOfPreparation.setText(String.valueOf(C.STAR));
    }

    @Override
    public void updateBasicStringHint(@NonNull String s) {
        tilBasicString.setHint(s);
    }

    @Override
    public void updateStringsAmountHint(@NonNull String s) {
        tilStringsAmount.setHint(s);
    }

    @Override
    public void updateIterationAmountHint(@NonNull String s) {
        tilIterationsAmount.setHint(s);
    }

    @Override
    public void updatePreparationResultOnMainThread(final @NonNull String result) {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                tvResultOfPreparation.setText(result);
            }
        });
    }

    @Override
    public void updateBurdenLengthOnMainThread(final int length) {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                final String burdenLengthText = rootContext.getString(R.string.totalBurdenInfo)
                        + C.SPACE + U.createReadableStringForLong(length);
                tvBurdenExplanation.setText(burdenLengthText);
            }
        });
    }

    @Override
    public void updateResultForLog(long resultNanoTime) {
        tvResultForLog.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void updateResultForDAL(long resultNanoTime) {
        tvResultForDAL.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void updateResultForVAL1(long resultNanoTime) {
        tvResultForVAL1.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void updateResultForVAL2(long resultNanoTime) {
        tvResultForVAL2.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void updateResultForVAL3(long resultNanoTime) {
        tvResultForVAL3.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void updateResultForSLVoid(long resultNanoTime) {
        tvResultForSLVoid.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void updateResultForSLInt(long resultNanoTime) {
        tvResultForSLInt.setText(U.adaptForUser(rootContext, resultNanoTime));
    }

    @Override
    public void informUser(int typeOfNotification, int stringId, int duration) {
        final String message = rootContext.getString(stringId);
        if (C.Choice.TOAST == typeOfNotification) {
            U.showToast(rootContext, message, duration);
        } else if (C.Choice.SNACKBAR == typeOfNotification) {
            U.showSnackbar(rootView, message, duration);
        } else {
            L.w(CN, message);
        }
    }

    @Override
    public void showBuildInfoDialog() {
        final String message = rootContext.getString(R.string.application) + C.SPACE
                + rootContext.getString(R.string.app_name) + C.N
                + rootContext.getString(R.string.versionCode) + C.SPACE + BuildConfig.VERSION_CODE + C.N
                + rootContext.getString(R.string.versionName) + C.SPACE + BuildConfig.VERSION_NAME + C.N
                + rootContext.getString(R.string.author) + C.SPACE + rootContext.getString(R.string.me);
        new AlertDialog.Builder(rootContext)
                .setTitle(rootContext.getString(R.string.aboutThisBuild))
                .setMessage(message)
                .create()
                .show();
    }

    @Override
    public void showBurdenInDialog(@NonNull String burden) {
        // TODO: 05.12.2017 move to the Dialog from support library \\
        final String title = rootContext.getString(R.string.totalBurdenLength) + C.SPACE + U.createReadableStringForLong(burden.length());
        final AlertDialog alertDialog = new AlertDialog.Builder(rootContext)
                .setTitle(title)
                .setMessage(burden)
                .create();
        alertDialog
                .show();
        toggleViewBurdenBusyStateOnMainThread(!alertDialog.isShowing());
    }

    @Override
    public void init() {

        tvStartingExplanation = rootView.findViewById(R.id.tvStartingExplanation);

        // burden preparation block \\
        tilBasicString = rootView.findViewById(R.id.tilBasicString);
        etBasicString = rootView.findViewById(R.id.tiedBasicString);
        etBasicString.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged() {
                logicLink.onBasicStringChanged();
            }
        });
        tilStringsAmount = rootView.findViewById(R.id.tilStringsAmount);
        etStringsAmount = rootView.findViewById(R.id.tiedStringsAmount);
        etStringsAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged() {
                logicLink.onStringsAmountChanged();
            }
        });
        bPrepareBurden = rootView.findViewById(R.id.bPrepareBurden);
        bPrepareBurden.setOnClickListener(this);
        bViewBurden = rootView.findViewById(R.id.bViewBurden);
        bViewBurden.setOnClickListener(this);
        tvResultOfPreparation = rootView.findViewById(R.id.tvResultOfPreparation);

        // iterations block \\
        tilIterationsAmount = rootView.findViewById(R.id.tilIterationsAmount);
        etIterationsAmount = rootView.findViewById(R.id.tiedIterationsAmount);
        etIterationsAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged() {
                logicLink.onIterationsAmountChanged();
            }
        });
        etIterationsAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // disabling this view's focused state somehow - we have to pass it somewhere \\
                    etBasicString.clearFocus();
                    etStringsAmount.clearFocus();
                    etIterationsAmount.clearFocus();
                    tvStartingExplanation.requestFocus();
                    // also should close keyboard right now \\
                    final InputMethodManager imm = (InputMethodManager)
                            rootContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(tvStartingExplanation.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
        bToggleIterations = rootView.findViewById(R.id.bToggleIterations);
        bToggleIterations.setOnClickListener(this);
        bViewAllResults = rootView.findViewById(R.id.bViewAllResults);
        bViewAllResults.setOnClickListener(this);
        tvBurdenExplanation = rootView.findViewById(R.id.tvBurdenExplanation);
        final String startingBurdenInfo = rootContext.getString(R.string.totalBurdenInfo) + C.SPACE + C.ZERO;
        tvBurdenExplanation.setText(startingBurdenInfo);
        tvResultForSout = rootView.findViewById(R.id.tvResultForSystemOutPrintln);
        tvResultForLog = rootView.findViewById(R.id.tvResultForStandardLog);
        tvResultForDAL = rootView.findViewById(R.id.tvResultForDAL);
        tvResultForVAL1 = rootView.findViewById(R.id.tvResultForVAL1);
        tvResultForVAL2 = rootView.findViewById(R.id.tvResultForVAL2);
        tvResultForVAL3 = rootView.findViewById(R.id.tvResultForVAL3);
        tvResultForSLVoid = rootView.findViewById(R.id.tvResultForSAL);
        tvResultForSLInt = rootView.findViewById(R.id.tvResultForVAL0);

        pdWait = new ProgressDialog(rootContext);
//        pdWait.setMessage(rootContext.getString(R.string.startingUp));
        pdWait.setIndeterminate(true);

        pbViewCreatedBurden = rootView.findViewById(R.id.pbViewCreatedBurden);

    } // init \\

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPrepareBurden:
                logicLink.onPrepareBurdenClick();
                break;
            case R.id.bViewBurden:
                logicLink.onViewBurdenClick();
                break;
            case R.id.bToggleIterations:
                logicLink.onToggleIterationsClick();
                break;
            case R.id.bViewAllResults:

                break;
        }
    }
}