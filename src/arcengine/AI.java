/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn Jones
 */
enum AIState {

    GiveBirth, Die, Home, Guard, Chase, Search, Evade, Relax, Attack
}

class AICommandPattern {

    Local step;
    Local steps;
    int collisions;
    Class target;
    AICommand command;
    AICommandPattern next;
    AICommandPattern prev;

    AICommandPattern() {

        this.command = RandomCommand();
        this.step = new Local((Random.rnd.nextInt(3) - Random.rnd.nextInt(3)), (Random.rnd.nextInt(3) - Random.rnd.nextInt(3)));
        this.steps = new Local(this.step);
    }

    AICommandPattern(int xprob, int yprob) {

        this.command = RandomCommand();
        this.step = new Local((Random.rnd.nextInt(xprob) - Random.rnd.nextInt(xprob)), (Random.rnd.nextInt(yprob) - Random.rnd.nextInt(yprob)));
        this.steps = new Local(this.step);
    }

    AICommandPattern(AICommand com, Local step) {
        this.command = com;
        this.step = new Local(step);
        this.steps = new Local(step);
    }

    AICommandPattern(AICommand com, Local step, AICommandPattern parant) {
        this.prev = parant;
        parant.next = this;
        this.command = com;
        this.step = new Local(step);
        this.steps = new Local(step);
    }

    AICommandPattern(AICommand com, Local step, Class target) {
        this.target = target;
        this.command = com;
        this.step = new Local(step);
        this.steps = new Local(step);
    }

    AICommandPattern(AICommand com, Local step, AICommandPattern parant, Class target) {
        this.prev = parant;
        parant.next = this;
        this.command = com;
        this.target = target;
        this.step = new Local(step);
        this.steps = new Local(step);
    }

    AICommandPattern(AICommandPattern copy) {
        this.command = copy.command;
        this.step = new Local(copy.step);
        this.steps = new Local(copy.step);
    }

    AICommandPattern(AICommandPattern parant, AICommandPattern copy) {
        this.prev = parant;
        parant.next = this;
        this.command = copy.command;
        this.step = new Local(copy.step);
        this.steps = new Local(copy.step);
    }

    AICommandPattern(AICommandPattern parant, AICommandPattern[] seed, int at) {
        this.prev = parant;
        parant.next = this;
        this.command = seed[at].command;
        this.step = new Local(seed[at].step);
        this.steps = new Local(seed[at].step);
    }

    AICommandPattern(AICommand com, Local step, AICommandPattern parant, AICommandPattern child) {
        this.prev = parant;
        parant.next = this;
        this.next = child;
        this.command = com;
        this.step = new Local(step);
        this.steps = new Local(step);
    }

    static Class RandomTarget() {
        switch (Random.d(10) + Random.d(10)) {
            case 2: {
                return Weapon.class;
            }
            case 3: {
                return Door.class;
            }
            case 4: {
                return Door.class;
            }
            case 5: {
                return Block.class;
            }
            case 6: {
                return Block.class;
            }
            case 7: {
                return Block.class;
            }
            case 8: {
                return Person.class;
            }
            case 9: {
                return Person.class;
            }
            case 10: {
                return Person.class;
            }
            case 11: {
                return Person.class;
            }
            case 12: {
                return Person.class;
            }
            case 13: {
                return Person.class;
            }
            case 14: {
                return Ammo.class;
            }
            case 15: {
                return Ammo.class;
            }
            case 16: {

                return Ammo.class;
            }
            case 17: {

                return Ground.class;
            }
            case 18: {

                return Ground.class;
            }
            case 19: {
                return Ground.class;
            }
            case 20: {
                return Weapon.class;
            }

        }
        return Person.class;
    }

    static AICommand RandomCommand() {
        switch (2 + Random.Next(20) + Random.Next(20)) {
            case 2:
                return AICommand.Grow;
            case 3:
                return AICommand.Shrink;
            case 4:
                return AICommand.AddRandomDirection;
            case 5:
                return AICommand.JumpCommand;
            case 6:
                return AICommand.Invert;
            case 7:
                return AICommand.Reverse;
            case 8:
                return AICommand.Swap;
            case 9:
                return AICommand.Insert;
            case 10:
                return AICommand.RotateClockwise;
            case 11:
                return AICommand.RotateAntiClockwise;
            case 12:
                return AICommand.RotateRandom;
            case 13:
                return AICommand.RandomDirection;
            case 14:
                return AICommand.ChaosForward;
            case 15:
                return AICommand.ChaosBackward;
            case 16:
                return AICommand.ChaosDownward;
            case 17:
                return AICommand.ChaosUpward;
            case 18:
                return AICommand.Forward;
            case 19:
                return AICommand.Backward;
            case 20:
                return AICommand.Clockwise90;
            case 21:
                return AICommand.AntiClockwise90;
            case 22:
                return AICommand.Clockwise45;
            case 23:
                return AICommand.AntiClockwise45;
            case 24:
                return AICommand.RandomTurn;
            case 25:
                return AICommand.RandomClockwise;
            case 26:
                return AICommand.RandomAntiClockwise;
            case 27:
                return AICommand.RandomX;
            case 28:
                return AICommand.RandomY;
            case 29:
                return AICommand.RotateClockwise;
            case 30:
                return AICommand.RotateAntiClockwise;
            case 31:
                return AICommand.RotateRandom;
            case 32:
                return AICommand.Insert;
            case 33:
                return AICommand.Swap;
            case 34:
                return AICommand.Reverse;
            case 35:
                return AICommand.Invert;
            case 36:
                return AICommand.JumpDirection;
            case 37:
                return AICommand.AddRandomCommand;
            case 38:
                return AICommand.Shrink;
            case 39:
                return AICommand.Grow;
            case 40:
                return AICommand.AddRandom;
        }
        return AICommand.RandomTurn;
    }

    static AICommand RandomDirection() {
        switch (2 + Random.Next(10) + Random.Next(10)) {
            case 2:
                return AICommand.RandomTurn;
            case 3:
                return AICommand.ChaosForward;
            case 4:
                return AICommand.ChaosBackward;
            case 5:
                return AICommand.ChaosDownward;
            case 6:
                return AICommand.ChaosUpward;
            case 7:
                return AICommand.Clockwise135;
            case 8:
                return AICommand.AntiClockwise135;
            case 9:
                return AICommand.Forward;
            case 10:
                return AICommand.Backward;
            case 11:
                return AICommand.Clockwise90;
            case 12:
                return AICommand.AntiClockwise90;
            case 13:
                return AICommand.Forward;
            case 14:
                return AICommand.Backward;
            case 15:
                return AICommand.Clockwise45;
            case 16:
                return AICommand.AntiClockwise45;
            case 17:
                return AICommand.RandomClockwise;
            case 18:
                return AICommand.RandomAntiClockwise;
            case 19:
                return AICommand.RandomX;
            case 20:
                return AICommand.RandomY;
        }
        return AICommand.RandomTurn;
    }

    void Reset() {
        this.steps = new Local(step);
    }

    void Randomize() {
        this.command = RandomCommand();
    }

    void Randomize(int xprob, int yprob) {
        this.command = RandomCommand();
        this.step = new Local((Random.rnd.nextInt(3) - Random.rnd.nextInt(3)), (Random.rnd.nextInt(3) - Random.rnd.nextInt(3)));
        this.steps = new Local(this.step);
    }

    public Local GetResult(Player piece) {

        if (steps.x == 0 && steps.y == 0) {
            steps = new Local(step);
        }
        int x = 0;
        int y = 0;
        if (steps.x > 0) {
            x = 1;
            steps.x--;
        } else if (steps.x < 0) {
            x = -1;
            steps.x++;
        }
        if (steps.y > 0) {
            y = 1;
            steps.y--;
        } else if (steps.y < 0) {
            y = -1;
            steps.y++;
        }
        Local go = new Local(x, y);
        switch (command) {
            case JumpCommand: {
                if (this.next != null) {
                    this.command = RandomCommand();
                    return this.next.GetResult(piece);
                }
            }
            case JumpDirection: {
                if (this.next != null) {
                    this.command = RandomDirection();
                    return this.next.GetResult(piece);
                }
            }
            case Reverse: {
                if (this.next != null) {
                    if (this.next.command == AICommand.Forward) {
                        this.next.command = AICommand.Backward;
                    } else if (this.next.command == AICommand.Clockwise90) {
                        this.next.command = AICommand.AntiClockwise90;
                    } else if (this.next.command == AICommand.Backward) {
                        this.next.command = AICommand.Forward;
                    } else if (this.next.command == AICommand.AntiClockwise90) {
                        this.next.command = AICommand.Clockwise90;
                    } //
                    else if (this.next.command == AICommand.AntiClockwise45) {
                        this.next.command = AICommand.Clockwise135;
                    } else if (this.next.command == AICommand.Clockwise135) {
                        this.next.command = AICommand.AntiClockwise45;
                    } else if (this.next.command == AICommand.Clockwise45) {
                        this.next.command = AICommand.AntiClockwise135;
                    } else if (this.next.command == AICommand.AntiClockwise135) {
                        this.next.command = AICommand.Clockwise45;
                    } //
                    else if (this.next.command == AICommand.ChaosForward) {
                        this.next.command = AICommand.ChaosBackward;
                    } else if (this.next.command == AICommand.ChaosDownward) {
                        this.next.command = AICommand.ChaosUpward;
                    } else if (this.next.command == AICommand.ChaosBackward) {
                        this.next.command = AICommand.ChaosForward;
                    } else if (this.next.command == AICommand.ChaosUpward) {
                        this.next.command = AICommand.ChaosDownward;
                    }
                }
                return go;
            }

            case Swap: {
                if (this.prev != null && this.next != null) {
                    AICommand swap1 = this.next.command;
                    AICommand swap2 = this.prev.command;
                    this.prev.command = swap1;
                    this.next.command = swap2;
                }
                return go;
            }
            case Insert: {
                this.command = RandomCommand();
                if (this.prev != null && this.next != null) {
                    if (this.prev.prev == null) {
                        this.prev = new AICommandPattern(this);
                    } else {
                        this.prev = new AICommandPattern(this.prev.prev, this);
                    }
                    this.next = new AICommandPattern(this, this);
                }
                this.command = RandomDirection();
                return go;
            }
            case AddRandom: {
                if (this.prev != null && this.next != null) {
                    this.command = RandomCommand();
                    if (this.prev.prev == null) {
                        this.prev = new AICommandPattern(this);
                    } else {
                        this.prev = new AICommandPattern(this.prev.prev, this);
                    }
                    this.command = RandomCommand();
                    this.next = new AICommandPattern(this, this);
                }
                this.command = RandomDirection();
                return go;
            }
            case Grow: {
                this.command = RandomDirection();
                if (this.prev != null && this.next != null) {
                    if (this.prev.prev == null) {
                        this.prev = new AICommandPattern(this);
                    } else {
                        this.prev = new AICommandPattern(this.prev.prev, this);
                    }
                    this.next = new AICommandPattern(this, this);

                }
                this.command = RandomCommand();
                return go;
            }
            case Shrink: {
                if (this.prev != null && this.next != null && this.next.next != null) {
                    this.next = this.next.next;
                    this.next.prev = this;
                }
                this.command = RandomCommand();
                return go;
            }
            case Invert: {
                if (this.prev != null && this.next != null && this.prev.prev != null) {
                    this.prev = this.prev.prev;
                    this.prev.next = this;
                }
                this.command = RandomCommand();

                return go;
            }

            case Forward: {
                return go;
            }
            case Backward: {
                return new Local(-go.x, -go.y);
            }
            case Clockwise45: {
                if (step.x > 0 && step.y > 0) {
                    return new Local(0, -go.y);//
                } else if (step.x < 0 && step.y < 0) {
                    return new Local(0, -go.y);//
                } else if (step.x < 0 && step.y > 0) {
                    return new Local(0, go.y);
                } else if (step.x > 0 && step.y < 0) {
                    return new Local(0, go.y);
                } else if (step.x == 0 && step.y > 0) {
                    return new Local(go.y, go.y);
                } else if (step.x == 0 && step.y < 0) {
                    return new Local(-go.y, go.y);
                } else if (step.x > 0 && step.y == 0) {
                    return new Local(go.x, -go.x);
                } else if (step.x < 0 && step.y == 0) {
                    return new Local(go.x, -go.x);
                }
            }
            case Clockwise135: {
                if (step.x > 0 && step.y > 0) {
                    return new Local(0, -go.y);//
                } else if (step.x < 0 && step.y < 0) {
                    return new Local(0, -go.y);//
                } else if (step.x < 0 && step.y > 0) {
                    return new Local(-go.x, 0);
                } else if (step.x > 0 && step.y < 0) {
                    return new Local(-go.x, 0);
                } else if (step.x == 0 && step.y > 0) {
                    return new Local(go.y, -go.y);
                } else if (step.x == 0 && step.y < 0) {
                    return new Local(go.y, -go.y);
                } else if (step.x > 0 && step.y == 0) {
                    return new Local(-go.x, -go.x);
                } else if (step.x < 0 && step.y == 0) {
                    return new Local(-go.x, -go.x);
                }
            }
            case AntiClockwise135: {
                if (step.x > 0 && step.y > 0) {
                    return new Local(-go.x, 0);//
                } else if (step.x < 0 && step.y < 0) {
                    return new Local(-go.x, 0);//
                } else if (step.x < 0 && step.y > 0) {
                    return new Local(0, -go.y);
                } else if (step.x > 0 && step.y < 0) {
                    return new Local(0, -go.y);
                } else if (step.x == 0 && step.y > 0) {
                    return new Local(-go.y, -go.y);
                } else if (step.x == 0 && step.y < 0) {
                    return new Local(-go.y, -go.y);
                } else if (step.x > 0 && step.y == 0) {
                    return new Local(-go.x, -go.x);
                } else if (step.x < 0 && step.y == 0) {
                    return new Local(-go.x, -go.x);
                }
            }
            case AntiClockwise45: {
                if (step.x > 0 && step.y > 0) {
                    return new Local(0, go.y);//
                } else if (step.x < 0 && step.y < 0) {
                    return new Local(0, go.y);//
                } else if (step.x < 0 && step.y > 0) {
                    return new Local(0, -go.y);
                } else if (step.x > 0 && step.y < 0) {
                    return new Local(0, -go.y);
                } else if (step.x == 0 && step.y > 0) {
                    return new Local(-go.y, -go.y);
                } else if (step.x == 0 && step.y < 0) {
                    return new Local(go.y, go.y);
                } else if (step.x > 0 && step.y == 0) {
                    return new Local(go.x, go.x);
                } else if (step.x < 0 && step.y == 0) {
                    return new Local(-go.x, -go.x);
                }
            }
            case Clockwise90: {
                if (step.x > 0 && step.y > 0) {
                    return new Local(go.x, -go.y);//
                } else if (step.x < 0 && step.y < 0) {
                    return new Local(go.x, -go.y);//
                } else if (step.x < 0 && step.y > 0) {
                    return new Local(-go.x, go.y);
                } else if (step.x > 0 && step.y < 0) {
                    return new Local(-go.x, go.y);
                } else if (step.x == 0 && step.y > 0) {
                    return new Local(go.y, 0);
                } else if (step.x == 0 && step.y < 0) {
                    return new Local(-go.y, 0);
                } else if (step.x > 0 && step.y == 0) {
                    return new Local(0, -go.x);
                } else if (step.x < 0 && step.y == 0) {
                    return new Local(0, -go.x);
                }
            }
            case AntiClockwise90: {

                if (step.x > 0 && step.y > 0) {
                    return new Local(-go.x, go.y);//
                } else if (step.x < 0 && step.y < 0) {
                    return new Local(-go.x, go.y);//
                } else if (step.x < 0 && step.y > 0) {
                    return new Local(go.x, -go.y);
                } else if (step.x > 0 && step.y < 0) {
                    return new Local(go.x, -go.y);
                } else if (step.x == 0 && step.y > 0) {
                    return new Local(-go.y, 0);
                } else if (step.x == 0 && step.y < 0) {
                    return new Local(go.y, 0);
                } else if (step.x > 0 && step.y == 0) {
                    return new Local(0, go.x);
                } else if (step.x < 0 && step.y == 0) {
                    return new Local(0, go.x);
                }
            }
            case ChaosForward: {
                this.step = new Local(step.x + steps.x, step.y + steps.y);
                this.command = RandomDirection();
                return go;

            }
            case ChaosBackward: {
                this.step = new Local(step.x - steps.x, step.y - steps.y);
                this.command = RandomDirection();
                return new Local(-go.x, -go.y);

            }
            case ChaosUpward: {
                this.step = new Local(step.x - steps.y, step.y + steps.x);
                this.command = RandomDirection();
                return go;

            }
            case ChaosDownward: {
                this.step = new Local(step.x + steps.y, step.y - steps.x);
                this.command = RandomDirection();

                return new Local(-go.x, -go.y);

            }
            case RotateClockwise: {
                if (this.next != null) {
                    if (this.next.command == AICommand.Forward) {
                        this.next.command = AICommand.Clockwise90;
                    } else if (this.next.command == AICommand.Clockwise90) {
                        this.next.command = AICommand.Backward;
                    } else if (this.next.command == AICommand.Backward) {
                        this.next.command = AICommand.AntiClockwise90;
                    } else if (this.next.command == AICommand.AntiClockwise90) {
                        this.next.command = AICommand.Forward;
                    }
                    //
                    if (this.next.command == AICommand.AntiClockwise45) {
                        this.next.command = AICommand.Clockwise45;
                    } else if (this.next.command == AICommand.Clockwise45) {
                        this.next.command = AICommand.Clockwise135;
                    } else if (this.next.command == AICommand.Clockwise135) {
                        this.next.command = AICommand.AntiClockwise135;
                    } else if (this.next.command == AICommand.AntiClockwise135) {
                        this.next.command = AICommand.Clockwise45;
                    }
                    //
                    if (this.next.command == AICommand.ChaosForward) {
                        this.next.command = AICommand.ChaosDownward;
                    } else if (this.next.command == AICommand.ChaosDownward) {
                        this.next.command = AICommand.ChaosBackward;
                    } else if (this.next.command == AICommand.ChaosBackward) {
                        this.next.command = AICommand.ChaosUpward;
                    } else if (this.next.command == AICommand.ChaosUpward) {
                        this.next.command = AICommand.ChaosForward;
                    }
                }
                return go;
            }
            case RotateRandom: {
                if (this.next != null) {
                    switch (Random.rnd.nextInt(11)) {
                        case 0:
                            this.next.command = AICommand.Clockwise90;
                            break;
                        case 1:
                            this.next.command = AICommand.Backward;
                            break;
                        case 2:
                            this.next.command = AICommand.AntiClockwise90;
                            break;
                        case 3:
                            this.next.command = AICommand.Forward;
                            break;
                        //
                        case 4:
                            this.next.command = AICommand.Clockwise45;
                            break;
                        case 5:
                            this.next.command = AICommand.Clockwise135;
                            break;
                        case 6:
                            this.next.command = AICommand.AntiClockwise135;
                            break;
                        case 7:
                            this.next.command = AICommand.Clockwise45;
                            break;
                        //
                        case 8:
                            this.next.command = AICommand.ChaosDownward;
                            break;
                        case 9:
                            this.next.command = AICommand.ChaosBackward;
                            break;
                        case 10:
                            this.next.command = AICommand.ChaosUpward;
                            break;
                        case 11:
                            this.next.command = AICommand.ChaosForward;
                            break;
                    }
                }
                return go;
            }
            case RotateAntiClockwise: {
                if (this.next != null) {
                    if (this.next.command == AICommand.AntiClockwise90) {
                        this.next.command = AICommand.Backward;
                    } else if (this.next.command == AICommand.Backward) {
                        this.next.command = AICommand.Clockwise90;
                    } else if (this.next.command == AICommand.Clockwise90) {
                        this.next.command = AICommand.Forward;
                    } else if (this.next.command == AICommand.Forward) {
                        this.next.command = AICommand.AntiClockwise90;
                    } //
                    else if (this.next.command == AICommand.AntiClockwise135) {
                        this.next.command = AICommand.Clockwise135;
                    } else if (this.next.command == AICommand.Clockwise135) {
                        this.next.command = AICommand.Clockwise45;
                    } else if (this.next.command == AICommand.Clockwise45) {
                        this.next.command = AICommand.AntiClockwise45;
                    } else if (this.next.command == AICommand.AntiClockwise45) {
                        this.next.command = AICommand.AntiClockwise135;
                    }
                    //
                    if (this.next.command == AICommand.ChaosUpward) {
                        this.next.command = AICommand.ChaosBackward;
                    } else if (this.next.command == AICommand.ChaosBackward) {
                        this.next.command = AICommand.ChaosDownward;
                    } else if (this.next.command == AICommand.ChaosDownward) {
                        this.next.command = AICommand.ChaosForward;
                    } else if (this.next.command == AICommand.ChaosForward) {
                        this.next.command = AICommand.ChaosUpward;
                    }
                }
                return go;
            }
            case RandomDirection: {
                if (this.next != null) {
                    switch (Random.rnd.nextInt(9)) {
                        case 0:
                            this.next.command = AICommand.Clockwise90;
                            break;
                        case 1:
                            this.next.command = AICommand.Backward;
                            break;
                        case 2:
                            this.next.command = AICommand.AntiClockwise90;
                            break;
                        case 3:
                            this.next.command = AICommand.Forward;
                            break;
                        //
                        case 4:
                            this.next.command = AICommand.Clockwise45;
                            break;
                        case 5:
                            this.next.command = AICommand.Clockwise135;
                            break;
                        case 6:
                            this.next.command = AICommand.AntiClockwise135;
                            break;
                        case 7:
                            this.next.command = AICommand.Clockwise45;
                            break;
                    }
                }
                return go;
            }
            case AddRandomDirection: {
                this.step = new Local(this.step.x + (Random.rnd.nextInt(2) - 1), this.step.y + (Random.rnd.nextInt(2) - 1));
                this.command = RandomDirection();
                return go;
            }
            case AddRandomCommand: {
                this.step = new Local(this.step.x + (Random.rnd.nextInt(2) - 1), this.step.y + (Random.rnd.nextInt(2) - 1));
                this.command = RandomCommand();
                return go;
            }
            case RandomTurn: {

                return new Local((Random.rnd.nextInt(2) - 1), (Random.rnd.nextInt(2) - 1));
            }
            case RandomX: {
                return new Local((Random.rnd.nextInt(2) - 1), go.y);
            }
            case RandomY: {
                return new Local(go.x, (Random.rnd.nextInt(2) - 1));
            }
            case RandomClockwise: {
                switch (Random.rnd.nextInt(15)) {
                    case 0:
                        return new Local(0, -go.y);//
                    case 1:
                        return new Local(0, -go.y);//
                    case 2:
                        return new Local(0, go.y);
                    case 3:
                        return new Local(0, go.y);
                    case 4:
                        return new Local(go.y, go.y);
                    case 5:
                        return new Local(-go.y, go.y);
                    case 6:
                        return new Local(go.x, -go.x);
                    case 7:
                        return new Local(go.x, -go.x);
                    case 8:
                        return new Local(go.x, -go.y);
                    case 9:
                        return new Local(go.x, -go.y);
                    case 10:
                        return new Local(-go.x, go.y);
                    case 11:
                        return new Local(-go.x, go.y);
                    case 12:
                        return new Local(go.y, 0);
                    case 13:
                        return new Local(-go.y, 0);
                    case 14:
                        return new Local(0, -go.x);
                    case 15:
                        return new Local(0, -go.x);
                }
                break;
            }
            case RandomAntiClockwise: {
                switch (Random.rnd.nextInt(15)) {
                    case 0:
                        return new Local(0, go.y);//
                    case 1:
                        return new Local(0, go.y);//
                    case 2:
                        return new Local(0, -go.y);
                    case 3:
                        return new Local(0, -go.y);
                    case 4:
                        return new Local(-go.y, -go.y);
                    case 5:
                        return new Local(go.y, go.y);
                    case 6:
                        return new Local(go.x, go.x);
                    case 7:
                        return new Local(-go.x, -go.x);
                    case 8:
                        return new Local(-go.x, go.y);//
                    case 9:
                        return new Local(-go.x, go.y);//
                    case 10:
                        return new Local(go.x, -go.y);
                    case 11:
                        return new Local(go.x, -go.y);
                    case 12:
                        return new Local(-go.y, 0);
                    case 13:
                        return new Local(go.y, 0);
                    case 14:
                        return new Local(0, go.x);
                    case 15:
                        return new Local(0, go.x);
                }
                break;
            }
        }
        return go;
    }
}

enum AICommand {

    Grow, Shrink,
    Forward, Backward, Clockwise90, AntiClockwise90, Clockwise45, AntiClockwise45, Clockwise135, AntiClockwise135, RandomDirection,
    //
    JumpCommand, JumpDirection,
    Invert, Reverse, Swap, Insert,
    //
    ChaosForward, ChaosBackward,
    ChaosDownward, ChaosUpward,
    //
    RotateClockwise, RotateAntiClockwise, RotateRandom,
    AddRandomDirection, AddRandomCommand, AddRandom,
    //
    RandomTurn, RandomClockwise, RandomAntiClockwise,
    RandomX, RandomY,
    NotACommand,
}

class AIElement extends MoveResult {

    Local fussyLocal;
    Local dirLocal;
    int collisions;
    int distance;
    MapElement target;

    public AIElement(MapElement piece, MapElement target, Local dir, int distance) {
        super();
        this.obj = piece;
        this.target = target;
        this.fussyLocal = new Local(piece.Local());
        this.dirLocal = dir;
        this.distance = distance;
    }

    void Evade(float f) {
        Vector dir = target.Subtract(obj);
        this.acc = new Acceleration(dir.GetNormal2D().Multiply(f / obj.mass.Value()));
        this.aa = new AngularAcceleration(acc.Value());
    }

    void Chase(float f) {
        Vector dir = obj.Subtract(target);
        this.acc = new Acceleration(dir.GetNormal2D().Multiply(f / obj.mass.Value()));
        this.aa = new AngularAcceleration(acc.Value());
    }
}

/**
 * Class AI
 */
public class AI extends Geograph {

    static Local CORE = new Local(256, 256);
    //
    // Fields
    //
    int trys;
    int level;
    Player user;
    AIState state;
    Geograph scan;
    Class primaryTarget;
    Class secondaryTarget;
    Class friend;
    Local primaryDefence;
    Local secondaryDefence;
    Local home;
    Local lastLocal;
    AIElement path;
    //java.util.ArrayList<Local> openList=new java.util.ArrayList<>();
    java.util.ArrayList<Local> closedList = new java.util.ArrayList<>();
    java.util.ArrayList<AIElement> nearLine = new java.util.ArrayList<>();
    java.util.ArrayList<AIElement> farLine = new java.util.ArrayList<>();
    java.util.ArrayList<AIState> rnaPattern = new java.util.ArrayList<>();
    //
    AICommandPattern currentPattern;
    int currentPatternNumber;
    int currentRna;
    AICommandPattern dnaPattern[];
    protected java.util.ArrayList<Player> agents;
    protected java.util.ArrayList<Person> people;
    protected java.util.ArrayList<Weapon> weapons;
    protected java.util.ArrayList<Ammo> ammo;
    protected java.util.ArrayList<Item> items;
    protected java.util.ArrayList<Block> blocks;
    protected java.util.ArrayList<AIElement> lineOfSight;
    protected java.util.ArrayList<java.util.ArrayList<AIElement>> linesOfSight;

    //
    // Constructors
    //
    public AI(Player user, Geograph scan, int level) {
        super(3, 3);
        this.level = level;
        this.scan = scan;
        this.user = user;
        this.home = new Local(user.Local());
        this.lastLocal = new Local(user.Local());
        this.currentPattern = new AICommandPattern();
        this.dnaPattern = new AICommandPattern[Block.SIZE];
        this.agents = new java.util.ArrayList<>(size);
        this.people = new java.util.ArrayList<>(size);
        this.weapons = new java.util.ArrayList<>(size);
        this.ammo = new java.util.ArrayList<>(size);
        this.items = new java.util.ArrayList<>(size);
        this.blocks = new java.util.ArrayList<>(size);
        this.lineOfSight = new java.util.ArrayList<>(size);
        this.linesOfSight = new java.util.ArrayList<>();

        RandomRna();
        this.SetScan(scan);
    }

    void RandomRna() {
        int s = user.mentalEnergy + user.mentalForce + user.mentalPower + level;
        this.currentRna = (Block.SIZE >> 2) + Random.rnd.nextInt(s);
        for (int i = 0; i < s; i++) {
            switch ((Random.d(10) + Random.d(10)) >> 1) {
                case 1: {
                    this.rnaPattern.add(AIState.Evade);
                    break;
                }
                case 2: {
                    this.rnaPattern.add(AIState.Relax);
                    break;
                }
                case 3: {
                    this.rnaPattern.add(AIState.Relax);
                    break;
                }
                case 4: {
                    this.rnaPattern.add(AIState.Search);
                    break;
                }
                case 5: {
                    this.rnaPattern.add(AIState.Guard);
                    break;
                }
                case 6: {
                    this.rnaPattern.add(AIState.Guard);
                    break;
                }
                case 7: {
                    this.rnaPattern.add(AIState.Chase);
                    break;
                }
                case 8: {
                    this.rnaPattern.add(AIState.Relax);
                    break;
                }
                case 9: {
                    this.rnaPattern.add(AIState.Relax);
                    break;

                }
                case 10: {
                    this.rnaPattern.add(AIState.Evade);
                    break;

                }

            }
        }
    }

    void NextRna() {
        this.currentRna++;
        if (this.currentRna >= this.rnaPattern.size()) {
            this.currentRna = 0;
        }
        this.state = this.rnaPattern.get(this.currentRna);
    }

    void NewRna() {
        this.state = this.rnaPattern.get(this.currentRna);
        switch ((Random.d(10) + Random.d(10)) >> 1) {
            case 1: {
                this.state = (AIState.Evade);
                break;
            }
            case 2: {
                this.state = (AIState.Relax);
                break;
            }
            case 3: {
                this.state = (AIState.Relax);
                break;
            }
            case 4: {
                this.state = (AIState.Search);
                break;
            }
            case 5: {
                this.state = (AIState.Guard);
                break;
            }
            case 6: {
                this.state = (AIState.Guard);
                break;
            }
            case 7: {
                this.state = (AIState.Chase);
                break;
            }
            case 8: {
                this.state = (AIState.Relax);
                break;
            }
            case 9: {
                this.state = (AIState.Relax);
                break;

            }
            case 10: {
                this.state = (AIState.Evade);
                break;

            }

        }
        this.rnaPattern.set(this.currentRna, this.state);
    }

    void RecompileDna() {
        AICommandPattern tide1 = this.currentPattern;
        AICommandPattern tide2 = this.dnaPattern[this.currentPatternNumber];
        AICommandPattern tides = new AICommandPattern();
        AICommandPattern t = tides;
        while (tide1 != null && tide2 != null) {
            int p = (Random.d(100));
            if (p < 50) {
                t.command = tide1.command;
                t.target = tide2.target;
                t.step = new Local(tide1.step.x + tide2.step.x, tide1.step.y - tide2.step.y);
            } else if (p > 50) {
                t.command = tide2.command;
                t.target = tide1.target;
                t.step = new Local(tide1.step.x - tide2.step.x, tide1.step.y + tide2.step.y);

            } else {
                t.command = AICommandPattern.RandomDirection();
                t.target = AICommandPattern.RandomTarget();
                t.step = new Local(Random.d(10) - Random.d(10), Random.d(10) - Random.d(10));

            }
            t.steps = new Local(t.step);
            if (t.next == null) {
                t.next = new AICommandPattern();
                t.next.prev = t;
                t.next.target = t.target;
            }
            tide1 = tide1.next;
            tide2 = tide2.next;

        }
        this.currentPattern = tides;
        int r = Random.rnd.nextInt(this.rnaPattern.size());
        int n = Random.rnd.nextInt(this.rnaPattern.size());
        int a = Random.rnd.nextInt(1 + (this.rnaPattern.size() >> 1));
        while (a >= 0) {
            AIState set = this.rnaPattern.get(r);
            this.rnaPattern.set(r, this.rnaPattern.get(n));
            this.rnaPattern.set(n, set);
            a--;
        }
        this.currentPattern = tides;
        System.out.println("Recompiled DNA");
    }

    void RecompileDna(Player with) {
        AICommandPattern tide1 = this.currentPattern;
        AICommandPattern tide2 = with.ai.dnaPattern[with.ai.currentPatternNumber];
        AICommandPattern tides = new AICommandPattern();
        AICommandPattern t = tides;
        while (tide1 != null && tide2 != null) {
            int p = Random.d(100);
            if (p < 50) {
                t.command = tide1.command;
                t.target = tide2.target;
                t.step = new Local(tide1.step.x + tide2.step.x, tide1.step.y - tide2.step.y);
            } else if (p > 50) {
                t.command = tide2.command;
                t.target = tide1.target;
                t.step = new Local(tide1.step.x - tide2.step.x, tide1.step.y + tide2.step.y);

            } else {
                t.command = AICommandPattern.RandomDirection();
                t.target = AICommandPattern.RandomTarget();
                t.step = new Local(Random.d(10) - Random.d(10), Random.d(10) - Random.d(10));

            }
            t.steps = new Local(t.step);
            if (t.next == null) {
                t.next = new AICommandPattern();
                t.next.prev = t;
                t.next.target = t.target;
            }
            tide1 = tide1.next;
            tide2 = tide2.next;

        }
        this.currentPattern = tides;
        int r = Random.rnd.nextInt(this.rnaPattern.size());
        int n = Random.rnd.nextInt(with.ai.rnaPattern.size());
        int a = Random.rnd.nextInt(1 + (this.rnaPattern.size() >> 1));
        while (a >= 0) {
            this.rnaPattern.set(r, with.ai.rnaPattern.get(n));
            a--;
        }
        System.out.println("Recompiled DNA");
    }

    //
    // Methods
    //
    void ClearAi() {
        this.agents.clear();
        this.people.clear();
        this.weapons.clear();
        this.ammo.clear();
        this.items.clear();
        this.blocks.clear();
        this.closedList.clear();
        //
        this.lineOfSight.clear();
        this.linesOfSight.clear();
        this.nearLine.clear();
        this.farLine.clear();
    }

    Local PatternScanLine(AICommandPattern pattern) {
        return new Local(pattern.step.x + cx, pattern.step.y + cy);
    }

    Local AddDirection(Local dir1, Local dir2) {
        Local loc = new Local(dir1.x + dir2.x, dir1.y + dir2.y);
        if (loc.x > 1) {
            loc.x = 1;
        }
        if (loc.y > 1) {
            loc.y = 1;
        }
        if (loc.x < -1) {
            loc.x = -1;
        }
        if (loc.y < -1) {
            loc.y = -1;
        }
        return loc;
    }

    Local RandomScanLine() {
        switch (Random.rnd.nextInt(3)) {
            case 0:
                return new Local(this.scan.size - 1, cy + Random.rnd.nextInt(cy - 1) - Random.rnd.nextInt(cy - 1));
            case 1:
                return new Local(0, cy + Random.rnd.nextInt(cy - 1) - Random.rnd.nextInt(cy - 1));
            case 2:
                return new Local(cx + Random.rnd.nextInt(cx - 1) - Random.rnd.nextInt(cx - 1), 0);
            case 3:
                return new Local(cx + Random.rnd.nextInt(cx - 1) - Random.rnd.nextInt(cx - 1), this.scan.size - 1);
        }
        return new Local(cx + Random.rnd.nextInt(cx - 1) - Random.rnd.nextInt(cx - 1), cy + Random.rnd.nextInt(cy - 1) - Random.rnd.nextInt(cy - 1));

    }

    boolean LineOfSight(Class target, Local to, boolean blocked) {
        int distance = 0;

        int dx2;
        int dy2;
        int ix;
        int iy;
        int nx;
        int ny;
        int i;
        int e;
        int x = scan.cx;
        int y = scan.cy;
        int dx = to.x - user.Local().x;
        int dy = to.y - user.Local().y;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                    ny = iy;
                } else {
                    ny = 0;
                }
                e += dy2;
                x += ix;
                distance++;
                //set line
                if (x >= 0 && y >= 0 && x < scan.size && y < scan.size) {
                    if (scan.element[x][y] != null) {
                        MapSection sec = scan.element[x][y].Get(0);
                        for (int j = 0; j < sec.contence.size(); j++) {
                            MapElement element = sec.contence.get(i);
                            /* if((element.getClass().equals(Block.class)  && blocked) ){
                             return false;
                             }else
                             if(element.pawn.getClass().equals(target)){
                             //System.out.println("dx"+dx+"dy"+dy+"x"+x+"y"+y);
                             this.lineOfSight.add(new AIElement(element.pawn,new Local(ix,ny),distance));
                             return true;
                             }else{
                             this.lineOfSight.add(new AIElement(element.pawn,new Local(ix,ny),distance));
                             }*/
                        }
                    }
                } else {
                    return false;
                }

            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                    nx = ix;
                } else {
                    nx = 0;
                }
                e += dx2;
                y += iy;
                //set line

                distance++;
                if (x >= 0 && y >= 0 && x < scan.size && y < scan.size) {
                    if (scan.element[x][y] != null) {
                        MapSection sec = scan.element[x][y].Get(0);
                        for (int j = 0; j < sec.contence.size(); j++) {
                            MapElement element = sec.contence.get(i);
                            /*if((element.getClass().equals(Block.class)  && blocked) ){
                             return false;
                             }else
                             if(element.getClass().equals(target)){
                             //System.out.println("dx"+dx+"dy"+dy+"x"+x+"y"+y);
                             this.lineOfSight.add(new AIElement(element,new Local(nx,iy),distance));
                             return true;
                             }else{
                             this.lineOfSight.add(new AIElement(element,new Local(nx,iy),distance));
                             }
                             */
                        }
                    }
                } else {

                    return false;
                }
            }
        }
        return false;
    }

    boolean LineOfSight(Class target, Local to, Class avoid, boolean blocked) {
        int distance = 0;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int nx;
        int ny;
        int i;
        int e;
        int x = scan.cx;
        int y = scan.cy;
        int dx = to.x - user.Local().x;
        int dy = to.y - user.Local().y;
        //System.out.println("dx"+dx+"dy"+dy+"x"+x+"y"+y);
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                    ny = iy;
                } else {
                    ny = 0;
                }
                e += dy2;
                x += ix;
                distance++;
                //set line
                if (x >= 0 && y >= 0 && x < scan.size && y < scan.size) {
                    if (scan.element[x][y] != null) {
                        MapSection sec = scan.element[x][y].Get(0);
                        for (int j = 0; j < sec.contence.size(); j++) {
                            MapElement element = sec.contence.get(i);
                            /*  if((element.getClass().equals(Block.class)  && blocked) || element.getClass().equals(avoid)){
                             return false;
                             }else
                             if(element.getClass().equals(target)){
                             //System.out.println("dx"+dx+"dy"+dy+"x"+x+"y"+y);
                             this.lineOfSight.add(new AIElement(element,new Local(ix,ny),distance));
                             return true;
                             }else{
                             this.lineOfSight.add(new AIElement(element,new Local(ix,ny),distance));
                             }*/
                        }
                    }
                } else {
                    return false;
                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                    nx = ix;
                } else {
                    nx = 0;
                }
                e += dx2;
                y += iy;
                //set line

                distance++;

                if (x >= 0 && y >= 0 && x < scan.size && y < scan.size) {
                    if (scan.element[x][y] != null) {
                        MapSection sec = scan.element[x][y].Get(0);
                        for (int j = 0; j < sec.contence.size(); j++) {
                            MapElement element = sec.contence.get(i);
                            /*if((element.getClass().equals(Block.class)  && blocked) || element.getClass().equals(avoid)){
                             return false;
                             }else
                             if(element.getClass().equals(target)){
                             //System.out.println("dx"+dx+"dy"+dy+"x"+x+"y"+y);
                             this.lineOfSight.add(new AIElement(element,new Local(nx,iy),distance));
                             return true;
                             }else{
                             this.lineOfSight.add(new AIElement(element,new Local(nx,iy),distance));
                             }*/
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    void SetScan(Geograph scan) {
        this.scan = scan;
        //System.out.println("Ai Scanning x"+this.scan.size+"y"+this.scan.size);
        for (int x = 0; x < this.scan.size; x++) {
            for (int y = 0; y < this.scan.size; y++) {
                MapSection sec = scan.element[x][y].Get(0);

                for (int i = 0; i < sec.contence.size(); i++) {
                    MapElement element = sec.contence.get(i);
                    /*
                     if(piece!=null){
                     //if(this.scan.size==32 && !(element.pawn.getClass().equals(Floor.class) || element.pawn.getClass().equals(Block.class)|| element.pawn.getClass().equals(Door.class)))System.out.println("Ai Rebel Element At x"+x+"y"+y+"p"+element.pawn.getClass());   
                  
                     if(element.pawn.getClass().equals(Robot.class)){
                     robots.add((Robot)element.pawn);   
                     }else if(element.pawn.getClass().equals(Rebel.class)){
                     rebels.add((Rebel)element.pawn);   
                     //System.out.println("Ai Rebel Element At x"+x+"y"+y+"p"+element.pawn.getClass()); 
                   
                     }else if(element.pawn.getClass().equals(Weapon.class)){
                     weapons.add((Weapon)element.pawn);   
                    
                     }else if(element.pawn.getClass().equals(Ammo.class)){
                     ammo.add((Ammo)element.pawn);   
                    
                     }else if(element.pawn.getClass().equals(Meds.class)){
                     meds.add((Meds)element.pawn);   
                    
                     }else if(element.pawn.getClass().equals(Block.class)){
                     blocks.add((Block)element.pawn);   
                    
                     }
                     }
                     element=element.next;
                     */
                }
            }
        }
    }
}
