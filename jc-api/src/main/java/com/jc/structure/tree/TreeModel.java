package com.jc.structure.tree;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

public final class TreeModel<T extends ITreeObject> implements Serializable {

	private static final Logger log = Logger.getLogger(TreeModel.class);
	private static final long serialVersionUID = -4213875368347776497L;

	//------------------------------ constructor ------------------------------

	public TreeModel(List<T> elements) {
		this(TreeUtils.toTree(elements));
	}

	@SuppressWarnings("unchecked")
    public TreeModel(TreeNode<T> root) {
		log.debug("TreeModel: new instance");
		if (root == null || root.getObject() == null) throw new IllegalStateException("TreeModel: NPE");

		_root = root;
		_keyPrefixLength = _defaultKeyPrefixLength;
		_comparator = new TreeNodeComparator<>();

		_keys.put(_root.getObject().getId(), getRootNodeKey());

		Iterator<TreeNode> p = _root.<T>childrenIterator();
		Iterator<TreeNode> prev = null;
		TreeNode<T> pitem;
		while (p.hasNext()) {
			pitem = p.next();
			_keys.put(pitem.getObject().getId(), prefixWithInt(pitem.getObject().getId()));

			if (pitem.hasChildren()) {
				prev = p;
				p = pitem.childrenIterator();
			} else if (prev != null && !p.hasNext()) {
				p = prev;
			}
		}
	}

	//------------------------------ root ------------------------------

	private final TreeNode<T> _root;

	public TreeNode<T> getRoot() {
		return _root;
	}

	public T getRootObject() {
		return _root.getObject();
	}

	private boolean _hidden = true;

	public void reset() {
		log.debug("reset");
		_hidden = true;
		_selectedNode = null;
	}

	//------------------------------ selected node ------------------------------

	private TreeNode<T> _selectedNode;

	public TreeNode<T> getSelectedNode() {
		return _selectedNode;
	}

	public void setSelectedNode(TreeNode<T> a) {
		_selectedNode = a;
	}

	public int getSelectedNodeId() {
		return _selectedNode != null ? _selectedNode.getObject().getId() : 0;
	}

	public boolean hasSelected() {
		return _selectedNode != null;
	}

	private static final String _defaultHiddenValue = "<valimata>";

	private String _hiddenValue = _defaultHiddenValue;

	public String getDoShowLabel() {
		return _selectedNode != null ? _selectedNode.getObject().getTitle() : _hiddenValue;
	}

	public String getDoHideLabel() {
		return "Sulge puu";
	}

	public boolean isHideTreeLinkVisible() {
		return true;
	}

	@SuppressWarnings("unchecked")
    public void toggleSelectedNode(String nodeKey) {
		int nodeItemId = Integer.parseInt(nodeKey.substring(_keyPrefixLength, nodeKey.length()));
		log.debug("setSelectedNode: nodeItemId=" + nodeItemId);

		if (_selectedNode != null && nodeItemId == _selectedNode.getObject().getId()) {
			setSelectedNode(null);
			return;
		}

		if (nodeItemId == _root.getObject().getId()) {
			_selectedNode = _root;
			return;
		}

		Iterator<TreeNode> p = _root.childrenIterator();
		Iterator<TreeNode> prev = null;
		TreeNode<T> pitem;
		while (p.hasNext()) {
			pitem = p.next();
			int poid = pitem.getObject().getId();
			if (poid == nodeItemId) {
				_selectedNode = pitem;
				return;
			}

			if (pitem.hasChildren()) {
				prev = p;
				p = pitem.childrenIterator();
			} else if (prev != null && !p.hasNext()) {
				p = prev;
			}
		}

		throw new IllegalArgumentException("setSelectedNode: no node item found: id=" + nodeItemId);
	}

	//------------------------------ prefix ------------------------------

	private final UUID _keyPrefix = UUID.randomUUID();
	private static final int _defaultKeyPrefixLength = 4;
	private final int _keyPrefixLength;

	private String getPrefix() {
		return _keyPrefix.toString().substring(0, _keyPrefixLength);
	}

	private String prefixWithInt(int id) {
		return getPrefix() + id;
	}

	@SuppressWarnings("unchecked")
    public String getObjectKey(Object o) {
		if (o == null) return null;
		int id = ((T) o).getId();
		return _keys.containsKey(id) ? _keys.get(id) : null;
	}

	public String getRootNodeKey() {
		return prefixWithInt(getRootObject().getId());
	}

	private final Map<Integer, String> _keys = new HashMap<>();

	public Collection<String> getAllKeys() {
		return _keys.values();
	}

	//------------------------------ comparator ------------------------------

	private final TreeNodeComparator<TreeNode<T>> _comparator;

	public Comparator<TreeNode<T>> getNodeComparator() {
		return _comparator;
	}

	private class TreeNodeComparator<Q extends TreeNode<T>> implements Comparator<Q> {
		public int compare(Q mida, Q millega) throws IllegalArgumentException {
			int result = privCompare(mida, millega);
			int midaId = mida.getObject().getId();
			int millegaId = millega.getObject().getId();
			log.debug("compare: midaId=" + midaId + ", millegaId=" + millegaId + ", result=" + result);
			return result * -1;
		}

		private int privCompare(Q mida, Q millega) {
			if (mida == null || millega == null || (mida.isRoot() && millega.isRoot()))
				throw new IllegalArgumentException("compare: mida == null || millega == null");
			int midaId = mida.getObject().getId();
			int millegaId = millega.getObject().getId();
			//otsime ruutu
			if (mida.isRoot()) return 1;
			if (millega.isRoot()) return -1;

			//kas on sama id'ga objekt
			if (midaId == millegaId) return 0;

			//võrdleme tasemeid
			int midalevel = mida.getLevel();
			int millegalevel = mida.getLevel();
			int levelCompare = Integer.valueOf(midalevel).compareTo(millegalevel);
			if (levelCompare != 0) return levelCompare;

			//sama vanema lapsed
			TreeNode midaParent = mida.getParent();
			if (midaParent.isNodeChild(millega)) {
				return Integer.valueOf(midaParent.indexOf(millega)).compareTo(midaParent.indexOf(mida));
			}

			return mida.dominates(millega) ? 1 : -1;
		}
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean b) {
		log.debug("setHidden: b=" + b);
		_hidden = b;
	}


	public String getHiddenValue() {
		return _hiddenValue;
	}

	public void setHiddenValue(String _hiddenValue) {
		this._hiddenValue = _hiddenValue;
	}
}
