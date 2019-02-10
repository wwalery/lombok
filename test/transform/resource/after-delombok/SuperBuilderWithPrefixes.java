class SuperBuilderWithPrefixes {
	int mField;
	int xOtherField;
	java.util.List<String> mItems;
	@java.lang.SuppressWarnings("all")
	public static abstract class SuperBuilderWithPrefixesBuilder<C extends SuperBuilderWithPrefixes, B extends SuperBuilderWithPrefixesBuilder<C, B>> {
		@java.lang.SuppressWarnings("all")
		private int field;
		@java.lang.SuppressWarnings("all")
		private int otherField;
		@java.lang.SuppressWarnings("all")
		private java.util.ArrayList<String> items;
		@java.lang.SuppressWarnings("all")
		protected abstract B self();
		@java.lang.SuppressWarnings("all")
		public abstract C build();
		@java.lang.SuppressWarnings("all")
		public B field(final int field) {
			this.field = field;
			return self();
		}
		@java.lang.SuppressWarnings("all")
		public B otherField(final int otherField) {
			this.otherField = otherField;
			return self();
		}
		@java.lang.SuppressWarnings("all")
		public B item(final String item) {
			if (this.items == null) this.items = new java.util.ArrayList<String>();
			this.items.add(item);
			return self();
		}
		@java.lang.SuppressWarnings("all")
		public B items(final java.util.Collection<? extends String> items) {
			if (this.items == null) this.items = new java.util.ArrayList<String>();
			this.items.addAll(items);
			return self();
		}
		@java.lang.SuppressWarnings("all")
		public B clearItems() {
			if (this.items != null) this.items.clear();
			return self();
		}
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		public java.lang.String toString() {
			return "SuperBuilderWithPrefixes.SuperBuilderWithPrefixesBuilder(field=" + this.field + ", otherField=" + this.otherField + ", items=" + this.items + ")";
		}
	}
	@java.lang.SuppressWarnings("all")
	private static final class SuperBuilderWithPrefixesBuilderImpl extends SuperBuilderWithPrefixesBuilder<SuperBuilderWithPrefixes, SuperBuilderWithPrefixesBuilderImpl> {
		@java.lang.SuppressWarnings("all")
		private SuperBuilderWithPrefixesBuilderImpl() {
		}
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		protected SuperBuilderWithPrefixesBuilderImpl self() {
			return this;
		}
		@java.lang.Override
		@java.lang.SuppressWarnings("all")
		public SuperBuilderWithPrefixes build() {
			return new SuperBuilderWithPrefixes(this);
		}
	}
	@java.lang.SuppressWarnings("all")
	protected SuperBuilderWithPrefixes(final SuperBuilderWithPrefixesBuilder<?, ?> b) {
		this.mField = b.field;
		this.xOtherField = b.otherField;
		java.util.List<String> items;
		switch (b.items == null ? 0 : b.items.size()) {
		case 0: 
			items = java.util.Collections.emptyList();
			break;
		case 1: 
			items = java.util.Collections.singletonList(b.items.get(0));
			break;
		default: 
			items = java.util.Collections.unmodifiableList(new java.util.ArrayList<String>(b.items));
		}
		this.mItems = items;
	}
	@java.lang.SuppressWarnings("all")
	public static SuperBuilderWithPrefixesBuilder<?, ?> builder() {
		return new SuperBuilderWithPrefixesBuilderImpl();
	}
}