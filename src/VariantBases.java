
public class VariantBases {
	
	Base10ToBaseX.Base[] bases = null;
	
	VariantBases(Base10ToBaseX.Base[] bases){
		this.bases = bases;
	}
	
	public  int getBaseXValueOnAtLevel(int on, int level) {
		if(level > bases.length || level < 1) {
			return 0; //INVALID Input
		}
		
		int rotation = 0; 
		int mask = bases[level-1].getLevelZeroMask();

		for(int i = 1; i < level; i++) {
			int localrotation = bases[i-1].getLevelOneRotation();
			mask = mask << localrotation;
			rotation += localrotation;
		}
		
		return (on & mask) >>> rotation;
	}
	
	public static void main(String[] args) {
		Base10ToBaseX.Base[] bases = {Base10ToBaseX.Base.Base65536, Base10ToBaseX.Base.BASE8, Base10ToBaseX.Base.BASE8, Base10ToBaseX.Base.BASE4, Base10ToBaseX.Base.BASE4, Base10ToBaseX.Base.BASE4, Base10ToBaseX.Base.BASE4, Base10ToBaseX.Base.BASE2, Base10ToBaseX.Base.BASE2};
		VariantBases vb = new VariantBases(bases);
//		System.out.println(vb.getBaseXValueOnAtLevel(235, 1)); //1110 1011 = 235
//		System.out.println(vb.getBaseXValueOnAtLevel(235, 2));
//		System.out.println(vb.getBaseXValueOnAtLevel(235, 3));
//		System.out.println(vb.getBaseXValueOnAtLevel(235, 4));
//		System.out.println(vb.getBaseXValueOnAtLevel(235, 5));
		System.out.println(vb.getBaseXValueOnAtLevel(1550201624, 1));
		System.out.println(vb.getBaseXValueOnAtLevel(1550201624, 2));
		System.out.println(vb.getBaseXValueOnAtLevel(1550201624, 3));
		System.out.println(vb.getBaseXValueOnAtLevel(1550201624, 4));
	}

	public int getLevelZeroMask(int i) {
		return bases[i-1].getLevelZeroMask();
	}
}