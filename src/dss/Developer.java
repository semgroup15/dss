package dss;

public @interface Developer {

    Value[] value();

    public static enum Value {
        /**
         * ARASON ISAR
         * <gusarais@student.gu.se>
         */
        ISAR,

        /**
         * NUUR AHMED
         * <gusnuuah@student.gu.se>
         */
        AHMED,

        /**
         * LIN QING
         * <guslinqi@student.gu.se>
         */
        JIM,

        /**
         * PETURSSON JONATAN
         * <guspetujo@student.gu.se>
         */
        JONATAN,

        /**
         * LIRIT MA THERESA CATALINA
         * <guslirma@student.gu.se>
         */
        TRIXIE,

        /**
         * NOGARA LEAL SEBASTIAN
         * <gusnogse@student.gu.se>
         */
        SEBASTIAN,
    }
}
