typedef i64 long
typedef i32 int

service ArithmeticService {
	long add(1:int x, 2:int y),
	long multiply(1:int x, 2:int y),
}