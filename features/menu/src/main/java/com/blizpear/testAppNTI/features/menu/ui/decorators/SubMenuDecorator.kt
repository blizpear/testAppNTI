import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blizpear.testAppNTI.features.menu.ui.viewholders.SubmenuHolder

class SubMenuDecorator(private val space: Int) : RecyclerView.ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		super.getItemOffsets(outRect, view, parent, state)

		if (parent.getChildViewHolder(view) is SubmenuHolder) {
			if (parent.getChildLayoutPosition(view).mod(2) == 1) {
				outRect.set(space, 0, space / 2, 0)
			} else {
				outRect.set(space / 2, 0, space, 0)
			}
		}
	}
}