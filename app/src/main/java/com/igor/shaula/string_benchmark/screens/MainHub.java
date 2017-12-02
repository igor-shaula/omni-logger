package com.igor.shaula.string_benchmark.screens;

import android.support.annotation.NonNull;

public interface MainHub {

    interface SystemLink {

        @NonNull
        String getAdaptedString(long resultNanoTime);

        void launchPreparation(@NonNull String basicString, int basicStringsCount);

        void launchAllMeasurements(int testRepetitionsCount);

        void stopTestingService();
    }

    interface UiLink {

        @NonNull
        String getBasicString();

        @NonNull
        String getRepetitionsCount();

        @NonNull
        String getIterationsAmount();

        void setLogicLink(@NonNull LogicLink logicLink);

        void toggleJobActiveUiState(boolean isRunning);

        void restoreResultViewStates();

        void showPreparationsResultOnMainThread(@NonNull long[] results);

        void updatePreparationResultOnMainThread(@NonNull String result);

        void updateResultForLog(long resultNanoTime);

        void updateResultForSAL(long resultNanoTime);

        void updateResultForDAL(long resultNanoTime);

        void updateResultForVAL(long resultNanoTime);

        void init();
    }

    interface LogicLink {

        void toggleJobState(boolean isRunning);

        void onFabClick();

        void prepareMainJob();

        void showPreparationsResult(int whatInfoToShow, long resultNanoTime);

        void unLinkDataTransport();

        void interruptPerformanceTest();
    }
}