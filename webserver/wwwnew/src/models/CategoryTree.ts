declare interface CategoryTree {
    id: number;
    text: string;
    expanded: boolean;
    isSelected: boolean;
    items: CategoryTree[];
    parentId: number;
}