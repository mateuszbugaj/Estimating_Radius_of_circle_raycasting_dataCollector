enum Shape{
    Circle{
        @Override
        public int value() {
            return 0;
        }

        @Override
        public Obj Object(float centerAngleMin, float centerAngleMax, float diameterMin, float diameterMax, float distMin, float distMax) {
            return new CircleObj().randomize(centerAngleMin, centerAngleMax, diameterMin, diameterMax, distMin, distMax);
        }

        @Override
        public Obj Object() {
            return Object(-60, 60, 40, 1200, 50, 499);
        }

        @Override
        public Obj Object(float centerAngle, float diameter, float dist) {
            return new CircleObj(centerAngle, dist, diameter);
        }


    },
    Line{
        @Override
        public int value() {
            return 1;
        }

        @Override
        public Obj Object(float centerAngleMin, float centerAngleMax, float diameterMin, float diameterMax, float distMin, float distMax) {
            return new LineObj().randomize(centerAngleMin, centerAngleMax, diameterMin, diameterMax, distMin, distMax);
        }

        @Override
        public Obj Object() {
            return Object(-9, 9, 200, 500, 20, 160);
        }

        @Override
        public Obj Object(float centerAngle, float diameter, float dist) {
            return null;
        }
    },
    Corner{
        @Override
        public int value() {
            return 2;
        }

        @Override
        public Obj Object(float centerAngleMin, float centerAngleMax, float diameterMin, float diameterMax, float distMin, float distMax) {
            return new Corner().randomize(centerAngleMin, centerAngleMax, diameterMin, diameterMax, distMin, distMax);
        }

        @Override
        public Obj Object() {
            return Object(-3, 3, 200, 450, -30, 30);
        }

        @Override
        public Obj Object(float centerAngle, float diameter, float dist) {
            return null;
        }
    },
    Nothing{
        @Override
        public int value() {
            return 3;
        }

        @Override
        public Obj Object(float centerAngleMin, float centerAngleMax, float diameterMin, float diameterMax, float distMin, float distMax) {
            return null;
        }

        @Override
        public Obj Object() {
            return null;
        }

        @Override
        public Obj Object(float centerAngle, float diameter, float dist) {
            return null;
        }
    };

    public abstract int value();
    public abstract Obj Object(float centerAngleMin, float centerAngleMax, float diameterMin, float diameterMax, float distMin, float distMax);
    public abstract Obj Object();
    public abstract Obj Object(float centerAngle, float diameter, float dist);
}